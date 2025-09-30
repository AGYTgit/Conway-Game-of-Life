.PHONY: all run

all: run

Life.class: Life.java RectPanel.java
	javac Life.java RectPanel.java

run: Life.class
	java Life
