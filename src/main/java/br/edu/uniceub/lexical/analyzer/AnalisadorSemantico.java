package br.edu.uniceub.lexical.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class AnalisadorSemantico {
	private static final int TABS = 65;

	public static void main(String[] args) throws IOException {

		try {
			ArrayList<String> strings = portugol();
			validateBody(strings);
			for (String s : strings)
				validateStatement(s);
			segregateVariables(strings);

			String fullString = strings.stream().reduce("", (a, b) -> a + "\n" + b);
			AnalisadorSintatico lexical = new AnalisadorSintatico(new StringReader(fullString));
			cabecalho();
			lexical.yylex();
			lexical.yytext();
		} catch (Exception e) {
			System.out
					.println("\nTexto inv�lido, cessando an�lise l�xica." + "\n" + "Erro: " + e.getLocalizedMessage());
		}
	}

	public static void validateBody(ArrayList<String> strings) throws Exception {
		if (!strings.get(0).equals("inicio") || !strings.get(strings.size() - 1).equals("fim"))
			throw new Exception("N�o come�a ou termina com inicio/fim.");
		else
			System.out.println("Come�o e fim validados.\n");
	}

	public static ArrayList<String> portugol() throws IOException {

		String rootPath = Paths.get("").toAbsolutePath().toString();
		String subPath = "/src/main/java/br/edu/uniceub/lexical/analyzer/";

		File file = new File(rootPath + subPath + "portugol.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		ArrayList<String> lines = new ArrayList<>();
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		fr.close(); // closes the stream and release the resources
		System.out.println("Conte�do do arquivo(linhas separadas por v�rgula)");
		System.out.println(lines); // returns a string that textually represents the object
		System.out.println();
		return lines;

	}

	private static void segregateVariables(ArrayList<String> strings) {

		for (int i = 0; i < strings.size(); i++) {

			if (strings.get(i).matches("\\s+[a-zA-Z]+\\(.*\\);") | strings.get(i).matches("[a-zA-Z]+\\(.*\\);")) {
				char[] result = new char[strings.get(i).lastIndexOf(")") - (strings.get(i).indexOf("(") + 1)];
				strings.get(i).getChars(strings.get(i).indexOf("(") + 1, strings.get(i).lastIndexOf(")"), result, 0);
				strings.set(i, strings.get(i).replaceAll(new String(result), "<vars>"));
				strings.set(i, strings.get(i).replace("\t", ""));
				strings.add(i + 1, new String(result));

				System.out.println("Trocando vari�veis da linha " + (i + 1) + ":\t" + strings.get(i));
			}
		}

	}

	private static void cabecalho() {
		String titulo = "|Lexema\t\t|\tToken\t\t\t|Posi��o\t|";
		System.out.println(StringUtils.repeat("-", TABS));
		System.out.println(titulo);
		System.out.println(StringUtils.repeat("-", TABS));
		return;
	}

	private static void validateStatement(String string) throws Exception {
		String s = string.replaceAll("\\s+", "");
		if (s.startsWith("se") | s.startsWith("inicio") | s.startsWith("fim") | s.startsWith("fim_se")
				| s.startsWith("entao") | s.startsWith("senao") | s.isEmpty())
			return;
		else if (!s.endsWith(";"))
			throw new Exception("\n Linha \"" + s + "\" n�o termina com \';\'");
	}
}
