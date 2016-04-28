package state

/**
  * Created by rob on 27/04/16.
  */
sealed abstract class AttributeName(name: String)

case object HEALTH extends AttributeName("Health")
case object INITIATIVE extends AttributeName("Initiative")