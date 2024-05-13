package pages.calculator;

public enum MemoryExecutors implements CalculatorExecutors {
	ALL_CLEAR("AC", "calcfunction('ac')"),
	CLEAR_ENTRY("CE", "calcfunction('ce')"),
	MEMORY_PLUS("M+", "calcfunction('m+')"),
	MEMORY_CALL("MR", "calcfunction('mr')"),
	MEMORY_IN("Min", "calcfunction('min')");

	private final String symbol;
	private final String script;

	MemoryExecutors(String symbol, String script) {
		this.symbol = symbol;
		this.script = script;
	}

	public String getScript() {
		return script;
	}

	public String getSymbol() {
		return symbol;
	}

	public static MemoryExecutors getExecutor(String symbol) {
		for (MemoryExecutors button : MemoryExecutors.values()) {
			if (button.symbol.equals(symbol)) {
				return button;
			}
		}
		return null;
	}
}
