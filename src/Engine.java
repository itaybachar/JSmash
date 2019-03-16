
public class Engine implements Runnable {
	
	private Thread	tEngine;
	private boolean	stop;
	private Input	i;
	
	public Stage stage;
	
	public Engine()
	{
		i = new Input();
		tEngine = new Thread(this);
		stop = false;
	}
	
	// At this point all GLFW init is complete
	public void init()
	{
		stage = new Stage();
		tEngine.start();
	}
	
	public void renderAll()
	{
		stage.render();
	}
	
	public void kill()
	{
		stop = true;
	}
	
	// Main logic loop
	public void run()
	{
		while (!stop) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}
			i.getButton(0);
		}
	}
}
