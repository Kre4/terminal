package ru.kre4.terminal.fs

import ru.kre4.terminal.fs.dsl.FileSystemNode
import ru.kre4.terminal.fs.dsl.root

class EmulatedFileSystem {

    private val rootNode: FileSystemNode.Directory =
        root {
            directory("projects") {
                project("gateway-benchmarks")
                project("terminal")
                project("GitAutoPrefix")
                project("BatchInsert")
            }
        } as FileSystemNode.Directory

    private var currentNode: FileSystemNode.Directory = rootNode

    fun currentPath(): String = buildPath(currentNode)

    sealed class CdResult {
        data object Success : CdResult()
        data class Failure(val message: String) : CdResult()
    }

    fun changeDirectory(to: String): CdResult {
        val normalized = resolveAbsolutePath(to)
        if (normalized == "/" || normalized == ".") {
            currentNode = rootNode
            return CdResult.Success
        }

        val target = findNode(normalized)
        return when {
            target == null -> CdResult.Failure("cd: no such file or directory: $to")
            target !is FileSystemNode.Directory -> CdResult.Failure("cd: not a directory: $to")
            else -> {
                currentNode = target
                CdResult.Success
            }
        }
    }

    private fun resolveAbsolutePath(path: String): String =
        if (path.startsWith("/")) normalizePosix(path) else normalizePosix("${currentPath()}/$path")

    private fun findNode(absolutePath: String): FileSystemNode? {
        val segments = absolutePath.removePrefix("/").split('/').filter { it.isNotEmpty() }
        var node: FileSystemNode = rootNode
        for (segment in segments) {
            if (node !is FileSystemNode.Directory) return null
            node = node.children.find { childName(it) == segment } ?: return null
        }
        return node
    }

    private fun buildPath(node: FileSystemNode.Directory): String {
        if (node === rootNode) return "/"
        val segments = mutableListOf<String>()
        var current: FileSystemNode? = node
        while (current != null && current !== rootNode) {
            segments.add(0, childName(current))
            current = current.parent
        }
        return "/" + segments.joinToString("/")
    }

    private fun childName(node: FileSystemNode): String =
        when (node) {
            is FileSystemNode.Directory -> node.name
            is FileSystemNode.File -> node.name
            is FileSystemNode.Project -> node.name
        }

    fun normalizePosix(path: String): String {
        val isAbsolute = path.startsWith("/")
        val stack = ArrayDeque<String>()
        for (segment in path.split('/').filter { it.isNotEmpty() && it != "." }) {
            when (segment) {
                ".." -> if (stack.isNotEmpty()) stack.removeLast()
                else if (!isAbsolute) stack.addLast("..")
                else -> stack.addLast(segment)
            }
        }
        val body = stack.joinToString("/")
        return when {
            isAbsolute -> "/$body"
            body.isEmpty() -> "."
            else -> body
        }
    }

}
