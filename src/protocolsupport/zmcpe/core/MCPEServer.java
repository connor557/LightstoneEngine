package protocolsupport.zmcpe.core;

import java.net.InetSocketAddress;
import java.util.List;

import org.bukkit.Bukkit;

import io.netty.channel.Channel;
import net.minecraft.server.v1_11_R1.EnumProtocolDirection;
import net.minecraft.server.v1_11_R1.NetworkManager;
import net.minecraft.server.v1_11_R1.ServerConnection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.common.LogicHandler;
import protocolsupport.protocol.pipeline.timeout.SimpleReadTimeoutHandler;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.utils.ReflectionUtils;
import protocolsupport.zmcpe.pipeline.PEPacketDecoder;
import protocolsupport.zmcpe.pipeline.PEPacketEncoder;
import protocolsupport.zplatform.impl.spigot.SpigotConnectionImpl;
import protocolsupport.zplatform.impl.spigot.SpigotMiscUtils;
import protocolsupport.zplatform.impl.spigot.network.SpigotChannelHandlers;
import protocolsupport.zplatform.impl.spigot.network.SpigotNetworkManagerWrapper;
import protocolsupport.zplatform.impl.spigot.network.handler.SpigotLegacyHandshakeListener;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketDecoder;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketEncoder;
import raknetserver.RakNetServer;
import raknetserver.RakNetServer.UserChannelInitializer;
import raknetserver.pipeline.raknet.RakNetPacketConnectionEstablishHandler.PingHandler;

public class MCPEServer {

	private final RakNetServer raknetserver;
	public MCPEServer(int port) {
		if (Bukkit.getServer().getOnlineMode()) {
			throw new IllegalStateException("MCPE doesn't support online-mode");
		}
		try {
			List<NetworkManager> networkmanagerlist = getNetworkManagerList();
			this.raknetserver = new RakNetServer(new InetSocketAddress(port), new PingHandler() {
				@Override
				public String getServerInfo(Channel channel) {
					return String.join(";", "MCPE", "test", "101", "1.0.3", String.valueOf(Bukkit.getOnlinePlayers().size()), String.valueOf(Bukkit.getMaxPlayers()));
				}
			}, new UserChannelInitializer() {
				@Override
				public void init(Channel channel) {
					NetworkManager networkmanager = new NetworkManager(EnumProtocolDirection.SERVERBOUND);
					SpigotNetworkManagerWrapper wrapper = new SpigotNetworkManagerWrapper(networkmanager);
					networkmanager.setPacketListener(new SpigotLegacyHandshakeListener(wrapper));
					ConnectionImpl connection = new SpigotConnectionImpl(wrapper);
					connection.storeInChannel(channel);
					ProtocolStorage.addConnection(channel.remoteAddress(), connection);
					connection.setVersion(ProtocolVersion.MINECRAFT_PE);
					NetworkDataCache cache = new NetworkDataCache();
					channel.pipeline().replace("rns-timeout", SpigotChannelHandlers.READ_TIMEOUT, new SimpleReadTimeoutHandler(30));
					channel.pipeline().addLast(new PEPacketEncoder(connection, cache));
					channel.pipeline().addLast(new PEPacketDecoder(connection, cache));
					channel.pipeline().addLast(SpigotChannelHandlers.ENCODER, new SpigotPacketEncoder());
					channel.pipeline().addLast(SpigotChannelHandlers.DECODER, new SpigotPacketDecoder());
					channel.pipeline().addLast(ChannelHandlers.LOGIC, new LogicHandler(connection));
					channel.pipeline().addLast(SpigotChannelHandlers.NETWORK_MANAGER, networkmanager);
					networkmanagerlist.add(networkmanager);
				}
			}, 0xFE);
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private static List<NetworkManager> getNetworkManagerList() throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		ServerConnection serverConnection = SpigotMiscUtils.getServer().an();
		try {
			return (List<NetworkManager>) ReflectionUtils.setAccessible(ServerConnection.class.getDeclaredField("pending")).get(serverConnection);
		} catch (NoSuchFieldException e) {
			return (List<NetworkManager>) ReflectionUtils.setAccessible(ServerConnection.class.getDeclaredField("h")).get(serverConnection);
		}
	}

	public void start() {
		raknetserver.start();
	}

	public void stop() {
		raknetserver.stop();
	}

}