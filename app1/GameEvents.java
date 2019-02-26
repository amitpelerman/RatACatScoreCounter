package app1;

public interface GameEvents {
	enum eventType {
		RoundFinished(true), GameFinished(true), NEWGAME(true);
		private boolean doEvent;

		eventType(boolean doEvent) {
			this.doEvent = doEvent;
		}

		boolean getDoEvent() {
			return doEvent;
		}
	}
}