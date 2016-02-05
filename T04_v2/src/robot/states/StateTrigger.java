package robot.states;

public interface StateTrigger {
	public void ObjectDetected(int stateID);
	public void ObjectIsGone(int stateID);
}
