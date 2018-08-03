import bot.commands.*
import bot.eventListeners.*
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser

import com.jagrosh.jdautilities.command.CommandClientBuilder
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.OnlineStatus

fun main(args: Array<String>) {
	val config = Parser().parse(args.getOrNull(0) ?: "config.json") as JsonObject

	val client = CommandClientBuilder()
			.addCommands(AddManagedChannel, AllowSpeakingOnce)
			.addCommand(Leave)
			.setPrefix("!")
			.setOwnerId(config.string("ownerID"))
			.build()

	val bot = JDABuilder(AccountType.BOT)
			.setStatus(OnlineStatus.ONLINE)
			.setAutoReconnect(true)
			.setToken(config.string("token"))
			.addEventListener(client)
			.addEventListener(OnMessageSend, OnChannelDeletion)
			.buildBlocking()
}