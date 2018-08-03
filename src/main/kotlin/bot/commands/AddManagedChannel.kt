package bot.commands

import bot.eventListeners.createRole
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.core.Permission

object AddManagedChannel : Command() {
	init {
		this.name = "addManagedChannel"
		this.help = "Creates a role for !allowSpeakingOnce for the specified channel."
		this.arguments = "<#channel>"
		this.userPermissions = arrayOf(Permission.MANAGE_CHANNEL)
	}

	override fun execute(event: CommandEvent) {
		val channel = event.message.mentionedChannels.getOrElse(0) {
			event.replyError("You must mention a channel!")
			return
		}

		createRole(channel)

		event.replySuccess("Created a role for channel <#${channel.id}>!")
	}
}