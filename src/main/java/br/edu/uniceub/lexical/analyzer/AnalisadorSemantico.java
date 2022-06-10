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

	public static void main(String[] args) throws IOException {

		try {
			ArrayList<String> strings = portugol();
			for (String s : strings)
				validateStatement(s);
			segregateVariables(strings);

			String fullString = strings.stream().reduce("", (a, b) -> a + "\n" + b);
			LexicalAnalyzer lexical = new LexicalAnalyzer(new StringReader(fullString));
			cabecalho();
			lexical.yylex();
			lexical.yytext();
		} catch (Exception e) {
			System.out.println(
					"\nTexto inválido, cessando análise léxica" + "." + "\n" + "Erro: " + e.getLocalizedMessage());
		}
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
		System.out.println("Conteúdo do arquivo(linhas separadas por vírgula)");
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
				strings.add(strings.indexOf(strings.get(i)+1), new String(result));

				System.out.println("Trocando variáveis da linha " + (i + 1) + ": " + strings.get(i));
			}
		}

	}

	private static void cabecalho() {
		String titulo = "|Lexema\t\t|\tToken\t\t\t|Posição\t|";
		System.out.println(StringUtils.repeat("-", 65));
		System.out.println(titulo);
		System.out.println(StringUtils.repeat("-", 65));
		return;
	}

	private static void validateStatement(String s) throws Exception {

		if (s.startsWith("se") | s.startsWith("fim_se") | s.startsWith("entao") | s.startsWith("senao") | s.isEmpty())
			return;
		else if (!s.endsWith(";"))
			throw new Exception("\n Linha \"" + s + "\" não termina com \';\'");
	}
}
