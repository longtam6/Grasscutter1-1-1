package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;
import java.util.Map;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "list", usage = "list [uid]", aliases = {"players"}, description = "commands.list.description")
public final class ListCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        Map<Integer, Player> playersMap = Grasscutter.getGameServer().getPlayers();
        boolean needUID = false;

        if (args.size() > 0) {
            needUID = args.get(0).equals("uid");
        }

        CommandHandler.sendMessage(sender, translate(sender, "commands.list.success", Integer.toString(playersMap.size())));

        if (Grasscutter.getConfig().server.game.gameOptions.CMD_ListOnline) {         
        
         if (playersMap.size() != 0) {
            StringBuilder playerSet = new StringBuilder();
            boolean finalNeedUID = needUID;

            playersMap.values().forEach(player -> {
                playerSet.append(player.getNickname());

                if (finalNeedUID) {
                    if (sender != null) {
                        playerSet.append(" <color=green>(")
                                .append(player.getUid())
                                .append(")</color>");
                    } else {
                        playerSet.append(" (")
                                .append(player.getUid())
                                .append(")");
                    }
                }

                playerSet.append(", ");
            });

            String players = playerSet.toString();
            CommandHandler.sendMessage(sender, players.substring(0, players.length() - 2));
         }
         
        }
    }
}
