package bot.commands

import bot.eventListeners.deleteRole
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.core.Permission

object Leave : Command() {
	init {
		this.name = "leave"
		this.help = "Instructs the bot to leave the server and delete the roles it created."
		this.userPermissions = arrayOf(Permission.KICK_MEMBERS)
	}

	override fun execute(event: CommandEvent) {
		for (channel in event.guild.textChannels) {
			deleteRole(channel)
		}

		event.channel.sendMessage("Deleted created roles!").complete()

		event.guild.leave().queue()
	}
}