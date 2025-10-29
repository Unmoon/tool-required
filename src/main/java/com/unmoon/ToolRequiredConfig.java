package com.unmoon;

import static com.unmoon.ToolRequiredConfig.CONFIG_GROUP;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(CONFIG_GROUP)
public interface ToolRequiredConfig extends Config
{
	String CONFIG_GROUP = "tool-required";
	String LAST_SEEN_VERSION = "0.0";

	@ConfigItem(
		keyName = "chopDown",
		name = "Remove Chop down",
		description = "Remove Chop down option from trees if you have no axe."
	)
	default boolean chopDown() {return true;}

	@ConfigItem(
		keyName = "mine",
		name = "Remove Mine",
		description = "Remove Mine option from rocks if you have no pickaxe."
	)
	default boolean mine() {return true;}

	@ConfigItem(
		keyName = "farming",
		name = "Remove Farming",
		description = "Remove Pick and Harvest option from relevant patches if you have no magic secateurs."
	)
	default boolean farm() {return true;}

}
