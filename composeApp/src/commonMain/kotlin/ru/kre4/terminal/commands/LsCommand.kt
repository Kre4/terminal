package ru.kre4.terminal.commands

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import ru.kre4.terminal.fs.dsl.FileSystemNode

class LsCommand : Command() {
    override fun execute(args: List<String>, context: CommandExecutionContext): CommandResult {
        var content = context.fileSystem.currentNode.children;
        content

        return CommandResult(
            lines = listOf(
                buildAnnotatedString {
                    content.forEach { node ->
                        when (node) {
                            is FileSystemNode.Directory ->
//                                withStyle(style = SpanStyle(color = Color.Red)) {
                                    appendLine(node.name)
//                                }

                            is FileSystemNode.File ->
//                                withStyle(style = SpanStyle(color = Color.Blue)) {
                                    appendLine(node.name)
//                                }

                            is FileSystemNode.Project ->
//                                withStyle(style = SpanStyle(color = Color.White)) {
                                    appendLine(node.name)
//                                }
                        }
                    }
                })
        )
    }

    override fun name(): String = "ls"

    override fun help(): List<CharSequence> = listOf("Lists all files and directories in current location.")
}
