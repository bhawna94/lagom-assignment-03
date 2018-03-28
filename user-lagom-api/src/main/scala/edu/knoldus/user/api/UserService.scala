package edu.knoldus.user.api



import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import play.api.libs.json.{Format, Json}

import scala.collection.mutable.ListBuffer


trait UserService extends Service {


  def getUser(id: Int):ServiceCall[NotUsed,ListBuffer[UserData]]

  def postUser():ServiceCall[UserData,Done]

  override final def descriptor = {

    import Service._
    // @formatter:off
    named("external")
      .withCalls(
        restCall(Method.POST,"/api/post/", postUser _),
        restCall(Method.GET,"/api/get/:id",getUser _)

      )
      .withAutoAcl(true)


  }
}
case class UserData(id: Int, name:String, organisation: String)

object UserData {

  implicit val format: Format[UserData] = Json.format[UserData]

}
