package ru.kre4.terminal.fs.dsl

import ru.kre4.terminal.fs.dsl.FileSystemNode.Directory


sealed class FileSystemNode(open val parent: FileSystemNode?) {
    class File(val name: String, override val parent: FileSystemNode) : FileSystemNode(parent)

    class Directory(
        val name: String,
        val children: MutableList<FileSystemNode> = mutableListOf(),
        override val parent: FileSystemNode? = null
    ) : FileSystemNode(null) {

        fun directory(name: String, block: Directory.() -> Unit) {
            val childDir = Directory(name, parent = this)
            childDir.block()
            children.add(childDir)
        }

        fun file(name: String) {
            children.add(File(name, this))
        }

        fun project(name: String) {
            children.add(Project(name, this))
        }
    }

    class Project(val name: String, override val parent: FileSystemNode) : FileSystemNode(parent)
}

fun root(block: Directory.() -> Unit): FileSystemNode {
    val root = Directory("root")
    root.block()
    return root
}
