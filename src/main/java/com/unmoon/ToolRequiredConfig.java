package com.unmoon;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("tool-required")
public interface ToolRequiredConfig extends Config
{
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

}
