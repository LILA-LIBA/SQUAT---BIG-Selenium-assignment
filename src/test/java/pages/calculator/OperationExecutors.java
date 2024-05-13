package pages.calculator;

public enum OperationExecutors implements CalculatorExecutors {
	DOT(".", "typesymbol('.')"),
	PLUS("+", "calcfunction('+')"),
	MINUS("-", "calcfunction('-')"),
	MULTIPLY("*", "calcfunction('*')"),
	DIVIDE("/", "calcfunction('/')"),
	EQUALS("=", "calcfunction('=')");

	private final String symbol;
	private final String script;

	OperationExecutors(String symbol, String script) {
		this.symbol = symbol;
		this.script = script;
	}

	public String getScript() {
		return script;
	}

	public String getSymbol() {
		return symbol;
	}

	public static OperationExecutors getExecutor(String symbol) {
		for (OperationExecutors button : OperationExecutors.values()) {
			if (button.symbol.equals(symbol)) {
				return button;
			}
		}
		return null;
	}
}
