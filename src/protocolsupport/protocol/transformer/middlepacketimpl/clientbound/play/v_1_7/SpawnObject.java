package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnObject extends MiddleSpawnObject<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		if (type == 78) { //skip armor stands
			return RecyclableEmptyList.get();
		}
		if (type == 71) {
			switch (objectdata) {
				case 0: {
					z -= 32;
					yaw = 128;
					break;
				}
				case 1: {
					x += 32;
					yaw = 64;
					break;
				}
				case 2: {
					z += 32;
					yaw = 0;
					break;
				}
				case 3: {
					x -= 32;
					yaw = 192;
					break;
				}
			}
		}
		if (type == 70) {
			final int id = objectdata & 0xFFFF;
			final int data = objectdata >> 12;
			objectdata = (id | (data << 16));
		}
		if ((type == 50) || (type == 70) || (type == 74)) {
			y += 16;
		}
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeVarInt(entityId);
		serializer.writeByte(type);
		serializer.writeInt(x);
		serializer.writeInt(y);
		serializer.writeInt(z);
		serializer.writeByte(pitch);
		serializer.writeByte(yaw);
		serializer.writeInt(objectdata);
		if (objectdata > 0) {
			serializer.writeShort(motX);
			serializer.writeShort(motY);
			serializer.writeShort(motZ);
		}
		return RecyclableSingletonList.<PacketData>create(PacketData.create(ClientBoundPacket.PLAY_SPAWN_OBJECT_ID, serializer));
	}

}
