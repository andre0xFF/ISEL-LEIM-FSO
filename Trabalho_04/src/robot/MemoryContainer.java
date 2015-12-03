public class MemoryContainer {
  public final static int AVOID_PORT = robot.S_2;
  public final static int ESCAPE_PORT = robot.S_1;

  private Boolean MODE_ESCAPE = false;
  private Boolean MODE_AVOID = false;
  private Boolean MODE_ROAM = false;

  public Boolean getMODE_ESCAPE() { return MODE_ESCAPE; }
  public Boolean getMODE_AVOID() { return MODE_AVOID; }
  public Boolean getMODE_ROAM() { return MODE_ROAM; }

  public void setMODE_ESCAPE(Boolean escape) { this.MODE_ESCAPE = escape;}
  public void setMODE_AVOID(Boolean avoid) { this.MODE_ESCAPE = avoid;}
  public void setMODE_ROAM(Boolean roam) { this.MODE_ESCAPE = roam;}
}
