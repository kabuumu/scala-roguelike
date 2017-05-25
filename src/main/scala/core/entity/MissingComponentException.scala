package core.entity

/**
  * Created by rob on 24/05/17.
  */
class MissingComponentException[T <: Component](entity: Entity)(implicit manifest: Manifest[T])
  extends Exception(s"$entity does not have ${manifest.runtimeClass.getName}")
