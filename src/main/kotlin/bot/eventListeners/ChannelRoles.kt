package bot.eventListeners

import net.dv8tion.jda.core.Permission
import net.dv8tion.jda.core.entities.TextChannel
import net.dv8tion.jda.core.events.Event
import net.dv8tion.jda.core.events.channel.text.TextChannelDeleteEvent
import net.dv8tion.jda.core.hooks.EventListener
import java.awt.Color

fun createRole(channel: TextChannel) {
	val controller = channel.guild.controller

	val role = controller.createRole()
			.setName("CanSpeakIn${channel.id}")
			.setColor(Color.GRAY)
			.setMentionable(false)
			.setHoisted(false)
			.setPermissions()
			.complete()

	channel.createPermissionOverride(role)
			.setAllow(Permission.MESSAGE_WRITE)
			.queue()
}

fun deleteRole(channel: TextChannel) {
	val guild = channel.guild
	val roles = guild.getRolesByName("CanSpeakIn${channel.id}", false)
	val role = roles.getOrNull(0) ?: return

	role.delete().queue()
}

object OnChannelDeletion : EventListener {
	override fun onEvent(event: Event) {
		if (event !is TextChannelDeleteEvent) return

		deleteRole(event.channel)
	}
}