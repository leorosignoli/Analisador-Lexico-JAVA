package br.edu.uniceub.lexical.analyzer;
import org.apache.commons.lang.StringUtils;
%%

%{
private static Integer posicao = 0;
private void imprimir(String descricao, String lexema) {
	int tabSize = descricao.length() > 16? 1 : 2;
	String tab = "\t";
    System.out.println("|"+lexema + "\t\t|\t" + descricao+ 
    	StringUtils.repeat(tab, tabSize)+"|\t"+(posicao++)+"\t|");
}
private void imprimirMetodo(String descricao, String lexema) {
	int tabSize = descricao.length() > 16? 1 : 2;
	String tab = "\t";
    System.out.println("|"+lexema + "\t|\t" + descricao+"\t|\t"+(posicao++)+"\t|");
    String args = lexema.substring(9, (lexema.length()-2));
}


%}


%class LexicalAnalyzer
%type void


BRANCO = [\n| |\t|\r]
ID = [_|a-z|A-Z][a-z|A-Z|0-9|_]*

SOMA = "+"
SUBTRACAO = "-"
MULTIPLICACAO = \*
DIVISAO = \/

INTEIRO = 0|[1-9][0-9]*
REAL= [0-9]+\.[0-9]

IMPRIMA= imprima\(.*\);

MAIOR = >
MENOR = <
MAIOR_IGUAL = >=
MENOR_IGUAL = <=
IGUAL = =
ATRIBUICAO = <-



%%

"se"                    { imprimir("Palavra reservada", yytext()); }
"fim_se"				{ imprimir("Palavra reservada", yytext()); }
"entao"                 { imprimir("Palavra reservada", yytext()); }
"senao"                 { imprimir("Palavra reservada", yytext()); }
{IMPRIMA}               { imprimirMetodo("Chamada � m�todo", yytext()); }
{BRANCO}                { }
{ID}                    { imprimir("Identificador", yytext()); }

{SOMA}                  { imprimir("Operador de soma", yytext()); }
{SUBTRACAO}             { imprimir("Operador de subtra��o", yytext()); }
{MULTIPLICACAO}   		{ imprimir("Opera��o aritm�tica", yytext()); }
{DIVISAO}          		{ imprimir("Opera��o aritm�tica", yytext()); }

{INTEIRO}               { imprimir("N�mero Inteiro", yytext()); }
{REAL}               { imprimir("N�mero Real", yytext()); }


{MAIOR}                 { imprimir("Opera��o aritm�tica", yytext()); }
{MENOR}                 { imprimir("Opera��o aritm�tica", yytext()); }
{MENOR_IGUAL}           { imprimir("Opera��o aritm�tica", yytext()); }
{MENOR_IGUAL}           { imprimir("Opera��o aritm�tica", yytext()); }
{IGUAL}          		{ imprimir("Opera��o aritm�tica", yytext()); }
{ATRIBUICAO}     		{ imprimir("Opera��o aritm�tica", yytext()); }



. { throw new RuntimeException("Caractere inv�lido " + yytext()); }