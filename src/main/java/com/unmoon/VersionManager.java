package com.unmoon;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;


@Slf4j
public class VersionManager {
    private final ConfigManager configManager;
    private final ToolRequiredConfig config;

    // Linked hashmap to preserve order of updates
    private static final Map<String, String> VERSION_UPDATES = new LinkedHashMap<>();

    static {
        VERSION_UPDATES.put("0.0.0", "Initial Release");
        VERSION_UPDATES.put("1.2.0", "Farming patches functionality added for magic secateurs");
    }

    // State tracking
    private boolean runConfigCheck;
    private int initializeTracker;

    @Inject
    public VersionManager(ConfigManager configManager, ToolRequiredConfig config) {
        this.configManager = configManager;
        this.config = config;
    }

    public void initialize() {
        runConfigCheck = hasUnseenUpdates();
        initializeTracker = 2;
    }

    public void onLogin() {
        initializeTracker = 2;
    }

    public boolean shouldShowMessage() {
        if (runConfigCheck && initializeTracker > 0 && --initializeTracker == 0) {
            return true;
        }
        return false;
    }

    public boolean isEnabled() {
        return runConfigCheck;
    }

    public void acknowledge() {
        updateLastVersionSeen();
        runConfigCheck = false;
    }

    public void resetVersionConfig()
    {
        configManager.setConfiguration(ToolRequiredConfig.CONFIG_GROUP, ToolRequiredConfig.LAST_VERSION_SEEN, "");
        initializeTracker = 2;
        runConfigCheck = true;
    }

    public String buildLoginMessage()
    {
        String lastVersionSeen = ToolRequiredConfig.LAST_VERSION_SEEN;
        StringBuilder message = new StringBuilder();
        message.append("Tool Required updated: ");
        boolean foundLastSeen = lastVersionSeen.isEmpty();
        boolean firstMessage = true;

        for (Map.Entry<String, String> entry : VERSION_UPDATES.entrySet()) {
            String version = entry.getKey();
            String changeMessage = entry.getValue();

            if (version.equals(lastVersionSeen)) {
                foundLastSeen = true;
                // we don't want to include this version again
                continue;
            }

            if (foundLastSeen) {
                if (!firstMessage) {
                    message.append("; ");
                }
                message.append(changeMessage);
                firstMessage = false;
            }
        }

        // add instruction to acknowledge via config
        message.append(". Click this message to not see this message again");
        return message.toString();
    }

    public boolean hasUnseenUpdates()
    {
        String lastVersionSeen = ToolRequiredConfig.LAST_VERSION_SEEN;
        String latestVersion = getLatestVersion();

        // If last seen equals latest in map, no unseen updates - you have told me linkedhashmap preserves insertion order the other day
        return !latestVersion.equals(lastVersionSeen);
    }

    public String getLatestVersion()
    {
        // Get the latest version by taking the last key in the map
        return VERSION_UPDATES.keySet().stream()
                .reduce((first, second) -> second)
                .orElse("");
    }

    public void updateLastVersionSeen() {
        configManager.setConfiguration(ToolRequiredConfig.CONFIG_GROUP, ToolRequiredConfig.LAST_VERSION_SEEN, getLatestVersion());
    }
}
