package ru.kre4.terminal.fs.dsl

import ru.kre4.terminal.fs.dsl.FileSystemNode.Directory


sealed class FileSystemNode(val name: String, val parent: FileSystemNode?) {
    class File(name: String, parent: FileSystemNode) : FileSystemNode(name, parent)

    class Directory(
        name: String,
        parent: FileSystemNode? = null,
        val children: MutableList<FileSystemNode> = mutableListOf(),
    ) : FileSystemNode(name, parent) {

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

    class Project(name: String, parent: FileSystemNode) : FileSystemNode(name, parent)
}

fun root(block: Directory.() -> Unit): FileSystemNode {
    val root = Directory("root")
    root.block()
    return root
}
