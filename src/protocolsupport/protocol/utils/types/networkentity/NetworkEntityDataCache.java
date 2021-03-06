package protocolsupport.protocol.utils.types.networkentity;

import org.bukkit.util.Vector;

import protocolsupport.utils.Utils;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class NetworkEntityDataCache {

	protected byte baseFlags = 0;
	protected boolean firstMeta = true;

	public byte getBaseFlags() {
		return baseFlags;
	}

	public boolean getBaseFlag(int bitpos) {
		return (baseFlags & (1 << (bitpos - 1))) != 0;
	}

	public void setBaseFlag(int bitpos, boolean value) {
		setBaseFlag(bitpos, value ? 1 : 0);
	}

	public void setBaseFlag(int bitpos, int value) {
		baseFlags &= ~(1 << (bitpos - 1));
		baseFlags |= (value << (bitpos - 1));
	}

	public void setBaseFlags(byte baseFlags) {
		this.baseFlags = baseFlags;
	}

	public boolean isFirstMeta() {
		return firstMeta;
	}

	public void setFirstMeta(boolean firstMeta) {
		this.firstMeta = firstMeta;
	}


	protected long peBaseFlags = 0;
	protected float sizeModifier = 1f;
	protected int attachedId = -1;
	protected Byte headRotation = null;
	protected int vehicleId = 0;
	protected Vector riderPosition = null;
	protected Float rotationlock = null;
	protected float maxHealth = 20f;
	protected ItemStackWrapper helmet = ItemStackWrapper.NULL;
	protected ItemStackWrapper chestplate = ItemStackWrapper.NULL;
	protected ItemStackWrapper leggings = ItemStackWrapper.NULL;
	protected ItemStackWrapper boots = ItemStackWrapper.NULL;
	protected ItemStackWrapper hand = ItemStackWrapper.NULL;
	protected ItemStackWrapper offhand = ItemStackWrapper.NULL;

	public long getPeBaseFlags() {
		return peBaseFlags;
	}

	public float getSizeModifier() {
		return sizeModifier;
	}

	public void setSizeModifier(float sizeModifier) {
		this.sizeModifier = sizeModifier;
	}

	public int getAttachedId() {
		return attachedId;
	}

	public void setAttachedId(int attachedId) {
		this.attachedId = attachedId;
	}

	public boolean getPeBaseFlag(int bitpos) {
		return (peBaseFlags & (1 << (bitpos - 1))) != 0;
	}

	public void setPeBaseFlag(int bitpos, boolean value) {
		setPeBaseFlag(bitpos, value ? 1 : 0);
	}

	public void setPeBaseFlag(int bitpos, long value) {
		peBaseFlags &= ~(1l << (bitpos - 1));
		peBaseFlags |= (value << (bitpos - 1));
	}

	public void setPeBaseFlags(long peBaseFlags) {
		this.peBaseFlags = peBaseFlags;
	}

	public void setHelmet(ItemStackWrapper helmet) {
		this.helmet = helmet;
	}

	public ItemStackWrapper getHelmet() {
		return this.helmet;
	}

	public void setChestplate(ItemStackWrapper chestplate) {
		this.chestplate = chestplate;
	}

	public ItemStackWrapper getChestplate() {
		return this.chestplate;
	}

	public void setLeggings(ItemStackWrapper leggings) {
		this.leggings = leggings;
	}

	public ItemStackWrapper getLeggings() {
		return this.leggings;
	}

	public void setBoots(ItemStackWrapper boots) {
		this.boots = boots;
	}

	public ItemStackWrapper getBoots() {
		return this.boots;
	}

	public void setHand(ItemStackWrapper hand) {
		this.hand = hand;
	}

	public ItemStackWrapper getHand() {
		return this.hand;
	}

	public void setOffHand(ItemStackWrapper offhand) {
		this.offhand = offhand;
	}

	public ItemStackWrapper getOffhand() {
		return this.offhand;
	}

	public void setHeadRotation(byte headRot) {
		this.headRotation = headRot;
	}

	public byte getHeadRotation(byte normalRotation) {
		if (headRotation != null) {
			return headRotation;
		}
		return normalRotation;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public boolean isRiding() {
		return vehicleId != 0;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Vector getRiderPosition() {
		return riderPosition;
	}

	public void setRiderPosition(Vector riderPosition) {
		this.riderPosition = riderPosition;
	}

	public Float getRotationLock() {
		return rotationlock;
	}

	public void setRotationLock(Float rotationlock) {
		this.rotationlock = rotationlock;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}


	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}