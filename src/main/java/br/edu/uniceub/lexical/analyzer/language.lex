package br.edu.uniceub.lexical.analyzer;
import org.apache.commons.lang.StringUtils;
%%

%{
private static Integer posicao = 0;
private void imprimir(String descricao, String lexema) {
	int tabSize = descricao.length() > 15? 1 : 2;
	int lexTabSize = lexema.length() > 6? 1 : 2;
	lexTabSize = lexema.length() == 15? 0 : lexTabSize;
	String tab = "\t";
    System.out.println("|"+lexema + StringUtils.repeat(tab, lexTabSize)+"|\t" + descricao+ 
    	StringUtils.repeat(tab, tabSize)+"|\t"+(posicao++)+"\t|");
}
private void imprimirMetodo(String descricao, String lexema) {

	int lexTabSize = lexema.length() > 11? 1 : 2;
	 lexTabSize = lexema.length() == 15? 0 : lexTabSize;
	
	String tab = "\t";
    System.out.println("|"+lexema + StringUtils.repeat(tab, lexTabSize)+"|\t" + descricao+"\t|\t"+(posicao++)+"\t|");
    String args = lexema.substring(9, (lexema.length()-2));
}
private void imprimirString(String descricao, String lexema) {	
	int tabSize = descricao.length() > 15? 1 : 2;
	int lexTabSize = lexema.length() > 15? 1 : 2;
	lexTabSize = lexema.length() == 15? 0 : lexTabSize;
	String tab = "\t";
    System.out.println("|"+"\"...\"\t\t|\t" + descricao+"\t\t\t|\t"+(posicao++)+"\t|");
    	}
%}


%class AnalisadorSintatico
%type void


BRANCO = [\n| |\t|\r]
ID = [_|a-z|A-Z][a-z|A-Z|0-9|_]*

DECLARACAO = [a-zA-Z]+:[a-zA-Z]+

SOMA = "+"
SUBTRACAO = "-"
MULTIPLICACAO = \*
DIVISAO = \/

INTEIRO = 0|[1-9][0-9]*
REAL= [0-9]+\.[0-9]
STRING= \".*\"

METODO= [a-zA-Z]+\(.*\)

MAIOR = >
MENOR = <
MAIOR_IGUAL = >=
MENOR_IGUAL = <=
IGUAL = =
ATRIBUICAO = <-



%%

"inicio"				{ imprimir("Palavra reservada", yytext()); }
"fim"					{ imprimir("Palavra reservada", yytext()); }
"se"                    { imprimir("Palavra reservada", yytext()); }
"fim_se"				{ imprimir("Palavra reservada", yytext()); }
"entao"                 { imprimir("Palavra reservada", yytext()); }
"senao"					{ imprimir("Palavra reservada", yytext()); }
"para"					{ imprimir("Palavra reservada", yytext()); }
"fim_para"				{ imprimir("Palavra reservada", yytext()); }
"ate"					{ imprimir("Palavra reservada", yytext()); }
"passo"                 { imprimir("Palavra reservada", yytext()); }
";"                 	{ imprimir("Fim de instrução", yytext()); }

{METODO}				{ imprimirMetodo("Chamada à método", yytext()); }
{BRANCO}                { }
{ID}                    { imprimir("Identificador", yytext()); }
{DECLARACAO}			{ imprimir("Declaracao", yytext()); }

{SOMA}                  { imprimir("Operador de soma", yytext()); }
{SUBTRACAO}             { imprimir("Operador de subtração", yytext()); }
{MULTIPLICACAO}   		{ imprimir("Operação aritmética", yytext()); }
{DIVISAO}          		{ imprimir("Operação aritmética", yytext()); }

{INTEIRO}               { imprimir("Número Inteiro", yytext()); }
{REAL}               	{ imprimir("Número Real", yytext()); }
{STRING}               	{ imprimirString("String", yytext()); }


{MAIOR}                 { imprimir("Comparador aritmético", yytext()); }
{MENOR}                 { imprimir("Comparador aritmético", yytext()); }
{MAIOR_IGUAL}           { imprimir("Comparador aritmético", yytext()); }
{MENOR_IGUAL}           { imprimir("Comparador aritmético", yytext()); }
{IGUAL}          		{ imprimir("Atribuicao de valor", yytext()); }
{ATRIBUICAO}     		{ imprimir("Atribuição", yytext()); }



. { throw new RuntimeException("Caractere inválido " + yytext()); }