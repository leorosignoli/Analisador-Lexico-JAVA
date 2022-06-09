package br.edu.uniceub.lexical.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class LanguageSextafFase {

	public static void main(String[] args) throws IOException {

		try {
			ArrayList<String> strings = portugol();
			for (String s : strings)
				if (s.startsWith("se") | s.startsWith("fim_se") | s.startsWith("entao") | s.startsWith("senao")
						| s.isEmpty())
					continue;
				else if (!s.endsWith(";"))
					throw new Exception("\n Linha \"" + s + "\" não termina com \';\'");
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
		System.out.println("Conteúdo do arquivo(linhas separadas por vírgula)\n");
		System.out.println(lines); // returns a string that textually represents the object
		return lines;

	}

	private static void cabecalho() {
		String titulo = "|Lexema\t\t|\tToken\t\t\t|Posição\t|";
		System.out.println(StringUtils.repeat("-", 65));
		System.out.println(titulo);
		System.out.println(StringUtils.repeat("-", 65));
		return;
	}
}
