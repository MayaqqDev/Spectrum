package de.dafuqs.spectrum.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import de.dafuqs.spectrum.cca.DDWorldEffectsComponent;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.apache.commons.lang3.StringUtils;

import static net.minecraft.server.command.CommandManager.literal;

public class SeasonCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("spectrum_seasons")
                .requires((source) -> source.hasPermissionLevel(2))
                .then(literal("query")
                        .executes(SeasonCommand::printSeasonAndPeriod))
                .then(literal("progress")
                        .then(literal("season")
                                .executes(context -> getProgress(context, false)))
                        .then(literal("period")
                                .executes(context -> getProgress(context, true))))
        );
    }

    private static int printSeasonAndPeriod(CommandContext<ServerCommandSource> context) {
        var source = context.getSource();
        var effects = DDWorldEffectsComponent.of(source.getWorld());
        source.sendFeedback(() -> Text.translatable("commands.spectrum.seasons.query", effects.getCurrentPeriod().getName(), effects.getCurrentSeason().getName()), false);
        return 1;
    }

    private static int getProgress(CommandContext<ServerCommandSource> context, boolean period) {
        var source = context.getSource();
        var effects = DDWorldEffectsComponent.of(source.getWorld());

        if (!period) {
            var progress = "" + ((float) effects.getSeasonalTime() % DDWorldEffectsComponent.SEASON_DURATION) * 100;
            source.sendFeedback(() -> Text.translatable("commands.spectrum.seasons.progress.season", StringUtils.left(progress, 3)), false);
        }
        else {
            var progress = "" + ((float) effects.getSeasonalTime() % DDWorldEffectsComponent.SEASON_PERIOD_INTERVAL) * 100;
            source.sendFeedback(() -> Text.translatable("commands.spectrum.seasons.progress.period", StringUtils.left(progress, 3)), false);
        }

        return 1;
    }
}
