package rogueLike.async

/**
  * Created by rob on 27/07/16.
  */
trait HasInitiative {
  val initiative: Initiative = Initiative.DEFAULT
  def initiative(f: Initiative => Initiative): HasInitiative
}
