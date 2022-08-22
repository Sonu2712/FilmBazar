package com.film.bazar.logger

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object FileZipper {

    fun zipDirectory(sourceFile: String, outputFile: String) {
        val fos = FileOutputStream(outputFile)
        val zipOut = ZipOutputStream(fos)
        val fileToZip = File(sourceFile)

        zipFile(fileToZip, fileToZip.name, zipOut)
        zipOut.close()
        fos.close()
    }

    private fun zipFile(fileToZip: File, fileName: String, zipOut: ZipOutputStream) {
        if (fileToZip.isHidden) {
            return
        }

        if (fileToZip.isDirectory) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(ZipEntry(fileName))
                zipOut.closeEntry()
            } else {
                zipOut.putNextEntry(ZipEntry("$fileName/"))
                zipOut.closeEntry()
            }
            val children = requireNotNull(fileToZip.listFiles())
            for (childFile in children) {
                zipFile(childFile, fileName + "/" + childFile.name, zipOut)
            }
            return
        }

        val fis = FileInputStream(fileToZip)
        val zipEntry = ZipEntry(fileName)
        zipOut.putNextEntry(zipEntry)
        val bytes = ByteArray(1024)
        var length: Int
        while (fis.read(bytes).also { length = it } >= 0) {
            zipOut.write(bytes, 0, length)
        }
        fis.close()
    }
}
