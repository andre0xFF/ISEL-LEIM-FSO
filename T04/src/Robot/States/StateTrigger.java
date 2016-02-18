package Robot.States;

public interface StateTrigger {
	public void ObjectDetected(int stateID);
	public void ObjectIsGone(int stateID);
}

