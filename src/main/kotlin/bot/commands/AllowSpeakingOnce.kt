package bot.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.core.Permission
import net.dv8tion.jda.core.entities.Member
import net.dv8tion.jda.core.entities.TextChannel

object AllowSpeakingOnce : Command() {
	init {
		this.name = "allowSpeakingOnce"
		this.help = "Allows a user to speak once in a channel."
		this.arguments = "<@user> [#channel]"
		this.userPermissions = arrayOf(Permission.MANAGE_CHANNEL)
	}

	val once = mutableMapOf<TextChannel, ArrayList<Member>>()

	override fun execute(event: CommandEvent) {
		val member = event.message.mentionedMembers.getOrElse(0) {
			event.replyError("No user was mentioned.")
			return
		}

		val channel = event.message.mentionedChannels.getOrElse(0) { event.textChannel }

		val controller = event.guild.controller
		val roles = event.guild.getRolesByName("CanSpeakIn${channel.id}", false)
		val role = roles.getOrElse(0) {
			event.replyError("This channel has not been set up with !addManagedChannel.")
			return
		}

		controller.addSingleRoleToMember(member, role).queue()

		this.addOnceMember(channel, member)

		event.replySuccess("<@${member.user.id}> can now speak once in <#${channel.id}>.")
	}

	private fun addOnceMember(channel: TextChannel, member: Member) {
		var members = this.once[channel]

		if (members == null) {
			this.once[channel] = arrayListOf()
			members = this.once[channel]
		}

		members!!.add(member)
	}
}