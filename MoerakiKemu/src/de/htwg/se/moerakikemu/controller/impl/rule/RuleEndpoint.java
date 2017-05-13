package de.htwg.se.moerakikemu.controller.impl.rule;

import java.awt.Point;
import java.util.List;
import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import de.htwg.se.moerakikemu.controller.impl.rule.actor.ActorRule;
import de.htwg.se.moerakikemu.controller.impl.rule.msg.MsgGetCells;
import de.htwg.se.moerakikemu.controller.impl.rule.msg.MsgPossibleInput;
import de.htwg.se.moerakikemu.controller.impl.rule.msg.MsgShiftedCell;
import de.htwg.se.moerakikemu.controller.impl.rule.msg.MsgShiftedIsland;
import de.htwg.se.moerakikemu.controller.impl.rule.msg.MsgStartDotPosCorrect;
import scala.concurrent.Await;
import scala.concurrent.Future;

public class RuleEndpoint {
	ActorRef actorRule;

	public RuleEndpoint() {
		ActorSystem system = ActorSystem.create();
		actorRule = system.actorOf(Props.create(ActorRule.class));
	}

	public boolean isStartDotPosCorrect(Point position) {
		MsgStartDotPosCorrect request = new MsgStartDotPosCorrect(position);
		MsgStartDotPosCorrect answer = (MsgStartDotPosCorrect) askRuleActor(request);
		return  answer.getResult();
	}

	public boolean isPositionPossibleInput(Point position) {
		MsgPossibleInput request = new MsgPossibleInput(position);
		MsgPossibleInput answer = (MsgPossibleInput) askRuleActor(request);
		return  answer.getResult();
	}

	public List<Point> getShiftedPositionsTemplateCells(Point position) {
		MsgShiftedCell request = new MsgShiftedCell(position);
		MsgShiftedCell answer = (MsgShiftedCell) askRuleActor(request);
		return answer.getResult();
	}

	public List<Point> getShiftedPositionsTemplateIslands(Point position) {
		MsgShiftedIsland request = new MsgShiftedIsland(position);
		MsgShiftedIsland answer = (MsgShiftedIsland) askRuleActor(request);
		return answer.getResult();
	}

	public List<List<Integer>> getCells() {
		MsgGetCells request = new MsgGetCells();
		MsgGetCells answer = (MsgGetCells) askRuleActor(request);
		return answer.getResult();
	}

	public Object askRuleActor(Object msgObject) {
		final Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
		final Future<Object> future = Patterns.ask(actorRule, msgObject, timeout);
		try {
			final Object result = Await.result(future, timeout.duration());
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
