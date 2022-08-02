package org.eu.maxelbk.emeraldmc.dsl

import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.*
import java.nio.file.attribute.*
import java.util.*
import java.util.function.BiPredicate
import java.util.stream.Stream

fun readFile(
    path: Path,
    runnable: (content: String) -> Unit = {},
): String {
    val content = Files.readString(path)
    runnable(content)
    return content
}

fun writeFile(path: Path, content: String) {
    if (!Files.exists(path)) Files.createFile(path)
    Files.writeString(path, content)
}

fun writeFile(
    path: Path,
    runnable: () -> String = { "" },
) {
    writeFile(path, runnable())
}

fun uri(uri: String): Path {
    return Path.of(uri)
}

fun file(
    path: Path,
    charset: Charset = StandardCharsets.UTF_8,
    runnable: File.() -> Unit = {},
): File {
    val file = File(path, charset)
    runnable(file)
    return file
}

data class File(
    val path: Path,
    val charset: Charset = StandardCharsets.UTF_8,
) {
    fun newInputStream(
        vararg options: OpenOption,
    ) = Files.newInputStream(path, *options)

    fun newOutputStream(
        vararg options: OpenOption,
    ) = Files.newOutputStream(path, *options)

    fun newByteChannel(
        options: Set<OpenOption>,
        vararg attrs: FileAttribute<*>,
    ) = Files.newByteChannel(path, options, *attrs)

    fun newByteChannel(
        vararg options: OpenOption,
    ) = Files.newByteChannel(path, *options)

    fun newDirectoryStream(
    ) = Files.newDirectoryStream(path)

    fun newDirectoryStream(
        glob: String,
    ) = Files.newDirectoryStream(path, glob)

    fun newDirectoryStream(
        filter: DirectoryStream.Filter<in Path>,
    ) = Files.newDirectoryStream(path, filter)

    fun createFile(
        vararg attrs: FileAttribute<*>,
    ) = Files.createFile(path, *attrs)

    fun createDirectory(
        vararg attrs: FileAttribute<*>,
    ) = Files.createDirectory(path, *attrs)

    fun createDirectories(
        vararg attrs: FileAttribute<*>,
    ) = Files.createDirectories(path, *attrs)

    fun createSymbolicLink(
        link: Path,
        vararg attrs: FileAttribute<*>,
    ) = createSymbolicLink(link, path, *attrs)

    fun createLink(
        link: Path
    ) = Files.createLink(link, path)

    fun delete(
    ) = Files.delete(path)

    fun deleteIfExists(
    ) = Files.deleteIfExists(path)

    fun copyTo(
        target: Path,
        vararg options: CopyOption,
    ) = Files.copy(path, target, *options)

    fun copyFrom(
        source: Path,
        vararg options: CopyOption,
    ) = Files.copy(source, path, *options)

    fun moveTo(
        target: Path,
        vararg options: CopyOption,
    ) = Files.move(path, target, *options)

    fun moveFrom(
        source: Path,
        vararg options: CopyOption,
    ) = Files.move(source, path, *options)

    fun readSymbolicLink(
    ) = Files.readSymbolicLink(path)

    fun getFileStore(
    ) = Files.getFileStore(path)

    fun isSameFile(
        path2: Path,
    ) = Files.isSameFile(path, path2)

    fun mismatch(
        path2: Path,
    ) = Files.mismatch(path, path2)

    val isHidden get() = Files.isHidden(path)

    fun probeContentType(
    ) = Files.probeContentType(path)

    fun <V : FileAttributeView> getFileAttributeView(
        type: Class<V>,
        vararg options: LinkOption,
    ) = Files.getFileAttributeView(path, type, *options)

    fun <A : BasicFileAttributes> readAttributes(
        type: Class<A>,
        vararg options: LinkOption,
    ) = Files.readAttributes(path, type, *options)

    fun setAttribute(
        attribute: String,
        value: Any,
        vararg options: LinkOption,
    ) = Files.setAttribute(path, attribute, value, *options)

    fun getAttribute(
        attribute: String,
        vararg options: LinkOption,
    ) = Files.getAttribute(path, attribute, *options)

    fun readAttributes(
        attributes: String,
        vararg options: LinkOption,
    ) = Files.readAttributes(path, attributes, *options)

    fun getPosixFilePermissions(
        vararg options: LinkOption,
    ): Set<PosixFilePermission> = Files.getPosixFilePermissions(path, *options)

    fun setPosixFilePermissions(
        perms: Set<PosixFilePermission>,
    ): Path = Files.setPosixFilePermissions(path, perms)

    var posixFilePermission
        get() = getPosixFilePermissions()
        set(value) { setPosixFilePermissions(value) }

    fun getOwner(
        vararg options: LinkOption,
    ): UserPrincipal = Files.getOwner(path, *options)

    fun setOwner(
        owner: UserPrincipal,
    ): Path = Files.setOwner(path, owner)

    var owner
        get() = getOwner()
        set(value) { setOwner(value) }

    val isSymbolicLink get() = Files.isSymbolicLink(path)

    fun isDirectory(
        vararg options: LinkOption,
    ) = Files.isDirectory(path, *options)

    val isDirectory get() = isDirectory()

    fun isRegularFile(
        vararg options: LinkOption,
    ) = Files.isRegularFile(path, *options)

    val isRegularFile get() = isRegularFile()

    fun getLastModifiedTime(
        vararg options: LinkOption,
    ): FileTime = Files.getLastModifiedTime(path, *options)

    fun setLastModifiedTime(
        time: FileTime,
    ): Path = Files.setLastModifiedTime(path, time)

    var lastModifiedTime
        get() = getLastModifiedTime()
        set(value) { setLastModifiedTime(value) }

    val size get() = Files.size(path)

    fun exists(
        vararg options: LinkOption,
    ) = Files.exists(path, *options)

    val exists get() = exists()

    fun notExists(
        vararg options: LinkOption
    ) = Files.notExists(path, *options)

    val notExists get() = notExists()

    val isReadable get() = Files.isReadable(path)

    val isWritable get() = Files.isWritable(path)

    val isExecutable get() = Files.isExecutable(path)

    fun newBufferedReader(
    ) = Files.newBufferedReader(path, charset)

    fun newBufferedWriter(
        vararg options: OpenOption,
    ) = Files.newBufferedWriter(path, charset, *options)

    fun copyFromStream(
        stream: InputStream,
        vararg options: CopyOption,
    ) = Files.copy(stream, path, *options)

    fun copyToStream(
        stream: OutputStream,
    ) = Files.copy(path, stream)

    fun readAllBytes(
    ) = Files.readAllBytes(path)

    fun readString(
    ) = Files.readString(path, charset)

    fun readAllLines(
    ) = Files.readAllLines(path, charset)

    fun write(
        bytes: ByteArray,
        vararg options: OpenOption,
    ) = Files.write(path, bytes, *options)

    fun write(
        lines: Iterable<CharSequence>,
        vararg options: OpenOption,
    ) = Files.write(path, lines, charset, *options)

    fun writeString(
        csq: CharSequence,
        vararg options: OpenOption,
    ) = Files.writeString(path, csq, charset, *options)

    fun list(
    ) = Files.list(path)

    fun walkFromHere(
        maxDepth: Int = Int.MAX_VALUE,
        vararg options: FileVisitOption,
    ) = walk(path, maxDepth, *options)

    fun walkFileTreeFromHere(
        options: Set<FileVisitOption> = EnumSet.noneOf(FileVisitOption::class.java),
        maxDepth: Int = Int.MAX_VALUE,
        visitor: FileVisitor<in Path>,
    ) = walkFileTree(path, options, maxDepth, visitor)

    fun findFromHere(
        maxDepth: Int,
        matcher: BiPredicate<Path, BasicFileAttributes>,
        vararg options: FileVisitOption,
    ) = find(path, maxDepth, matcher, *options)

    fun lines(
    ) = Files.lines(path, charset)

    companion object {
        fun createTempFile(
            dir: Path,
            prefix: String,
            suffix: String,
            vararg attrs: FileAttribute<*>,
        ) = Files.createTempFile(dir, prefix, suffix, *attrs)

        fun createTempFile(
            prefix: String,
            suffix: String,
            vararg attrs: FileAttribute<*>,
        ) = Files.createTempFile(prefix, suffix, *attrs)

        fun createTempDirectory(
            dir: Path,
            prefix: String,
            vararg attrs: FileAttribute<*>,
        ) = Files.createTempDirectory(dir, prefix, *attrs)

        fun createTempDirectory(
            prefix: String,
            vararg attrs: FileAttribute<*>,
        ) = Files.createTempDirectory(prefix, *attrs)

        fun createSymbolicLink(
            link: Path,
            target: Path,
            vararg attrs: FileAttribute<*>,
        ): Path = Files.createSymbolicLink(link, target, *attrs)

        fun walkFileTree(
            start: Path,
            options: Set<FileVisitOption> = EnumSet.noneOf(FileVisitOption::class.java),
            maxDepth: Int = Int.MAX_VALUE,
            visitor: FileVisitor<in Path>,
        ): Path = Files.walkFileTree(start, options, maxDepth, visitor)

        fun walk(
            start: Path,
            maxDepth: Int = Int.MAX_VALUE,
            vararg options: FileVisitOption,
        ): Stream<Path> = Files.walk(start, maxDepth, *options)

        fun find(
            start: Path,
            maxDepth: Int,
            matcher: BiPredicate<Path, BasicFileAttributes>,
            vararg options: FileVisitOption,
        ): Stream<Path> = Files.find(start, maxDepth, matcher, *options)
    }
}
