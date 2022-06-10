package br.edu.uniceub.lexical.analyzer;
import org.apache.commons.lang.StringUtils;
%%

%{
private static Integer posicao = 0;
private void imprimir(String descricao, String lexema) {
	int tabSize = descricao.length() > 15? 1 : 2;
	String tab = "\t";
    System.out.println("|"+lexema + "\t\t|\t" + descricao+ 
    	StringUtils.repeat(tab, tabSize)+"|\t"+(posicao++)+"\t|");
}
private void imprimirMetodo(String descricao, String lexema) {

	int lexTabSize = lexema.length() > 15? 1 : 2;
	 lexTabSize = lexema.length() == 15? 0 : lexTabSize;
	
	String tab = "\t";
    System.out.println("|"+lexema + StringUtils.repeat(tab, lexTabSize)+"|\t" + descricao+"\t|\t"+(posicao++)+"\t|");
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
STRING= "[a-zA-Z]+"

IMPRIMA= imprima\(.*\)

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
";"                 	{ imprimir("Fim de instru��o", yytext()); }

{IMPRIMA}               { imprimirMetodo("Chamada � m�todo", yytext()); }
{BRANCO}                { }
{ID}                    { imprimir("Identificador", yytext()); }

{SOMA}                  { imprimir("Operador de soma", yytext()); }
{SUBTRACAO}             { imprimir("Operador de subtra��o", yytext()); }
{MULTIPLICACAO}   		{ imprimir("Opera��o aritm�tica", yytext()); }
{DIVISAO}          		{ imprimir("Opera��o aritm�tica", yytext()); }

{INTEIRO}               { imprimir("N�mero Inteiro", yytext()); }
{REAL}               	{ imprimir("N�mero Real", yytext()); }
{STRING}               	{ imprimir("String", yytext()); }


{MAIOR}                 { imprimir("Comparador aritmp�tico", yytext()); }
{MENOR}                 { imprimir("Comparador aritmp�tico", yytext()); }
{MAIOR_IGUAL}           { imprimir("Comparador aritmp�tico", yytext()); }
{MENOR_IGUAL}           { imprimir("Comparador aritmp�tico", yytext()); }
{IGUAL}          		{ imprimir("Atribuicao de valor", yytext()); }
{ATRIBUICAO}     		{ imprimir("Atribui��o", yytext()); }



. { throw new RuntimeException("Caractere inv�lido " + yytext()); }