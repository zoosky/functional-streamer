package functionalstreamer

case class FileModel(path: String, name: String, tpe: FileType)

sealed trait FileType
object FileType {
  case object Directory extends FileType
  case object Misc      extends FileType
}
