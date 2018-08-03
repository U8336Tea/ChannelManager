package bot.eventListeners

import bot.commands.AllowSpeakingOnce
import net.dv8tion.jda.core.events.Event
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.core.hooks.EventListener

object OnMessageSend : EventListener {
	override fun onEvent(event: Event) {
		if (event !is GuildMessageReceivedEvent) return

		val author = event.member
		val guild = event.guild
		val channel = event.message.textChannel

		if (AllowSpeakingOnce.once[channel]?.contains(author) == true) {
			val controller = guild.controller
			val role = guild.getRolesByName("CanSpeakIn${event.channel.id}", false)[0]
			controller.removeSingleRoleFromMember(event.member, role).queue()

			AllowSpeakingOnce.once[channel]!!.remove(author)
		}
	}
}