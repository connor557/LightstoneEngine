package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleVehiclePassengers;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class VehiclePassengers extends MiddleVehiclePassengers {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SET_PASSENGERS_ID);
		VarNumberSerializer.writeVarInt(serializer, vehicleId);
		ArraySerializer.writeVarIntVarIntArray(serializer, passengersIds);
		return RecyclableSingletonList.create(serializer);
	}

}
