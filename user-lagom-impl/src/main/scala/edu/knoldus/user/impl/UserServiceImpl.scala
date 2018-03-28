package edu.knoldus.user.impl
import akka.stream.actor.ActorPublisherMessage.Request
import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import edu.knoldus.emp.api.{Employee, EmployeeService}
import edu.knoldus.user.api.{UserData, UserService}

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class UserServiceImpl(externalservice: EmployeeService)(implicit ec:ExecutionContext) extends UserService {

  override def postUser(): ServiceCall[UserData, Done] = ServiceCall{
  request=>
      val empdetails = Employee(request.id,request.name,request.organisation)
      val method: Future[ListBuffer[Employee]] =  externalservice.postEmployee().invoke(empdetails)
      method.map(response => response)
      Future.successful(Done)
  }

  override def getUser(id: Int): ServiceCall[NotUsed, ListBuffer[UserData]] = ServiceCall{
    request =>
      val gettingEmployeeById: Future[ListBuffer[Employee]] = externalservice.getEmployeeById(id).invoke()
      val x = Await.result(gettingEmployeeById,Duration.Inf)
      val y =  x.map(ele => UserData(ele.id,ele.name,ele.organisation))
      Future.successful(y)
  }
}




