package pages.calculator;

public enum NumberExecutors implements CalculatorExecutors {
	ZERO("0", "typenumber(0)"),
	ONE("1", "typenumber(1)"),
	TWO("2", "typenumber(2)"),
	THREE("3", "typenumber(3)"),
	FOUR("4", "typenumber(4)"),
	FIVE("5", "typenumber(5)"),
	SIX("6", "typenumber(6)"),
	SEVEN("7", "typenumber(7)"),
	EIGHT("8", "typenumber(8)"),
	NINE("9", "typenumber(9)");

	private final String symbol;
	private final String script;

	NumberExecutors(String symbol, String script) {
		this.symbol = symbol;
		this.script = script;
	}

	public String getScript() {
		return script;
	}

	public String getSymbol() {
		return symbol;
	}

	public static NumberExecutors getExecutor(String symbol) {
		for (NumberExecutors button : NumberExecutors.values()) {
			if (button.symbol.equals(symbol)) {
				return button;
			}
		}
		return null;
	}
}
