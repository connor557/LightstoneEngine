package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.login.v_1_7;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.login.MiddleLoginDisconnect;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class LoginDisconnect extends MiddleLoginDisconnect<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeString(messageJson);
		return RecyclableSingletonList.<PacketData>create(PacketData.create(ClientBoundPacket.LOGIN_ENCRYPTION_BEGIN_ID, serializer));
	}

}
