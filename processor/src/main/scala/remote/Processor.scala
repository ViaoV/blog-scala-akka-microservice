package remote

import akka.actor._
import shared.services.messages.TestPrimality


object PrimeService extends App  {
  val system = ActorSystem("PrimeService")
  val remoteActor = system.actorOf(Props[PrimeTesterActor], name = "PrimeActor")
  remoteActor ! "The RemoteActor is alive"
}

class PrimeTesterActor extends Actor {
  def testNumber(n:Int) = ((1 to n/2).filter(i => n % i == 0).length) == 1
  def receive = {
    case cmd: TestPrimality => sender ! testNumber(cmd.number)
  }
}
