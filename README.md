# CSCI 466: Compilers

## CatScript

In this course we will be creating a small programming language called CatScript

### CatScript Grammar

```
catscript_program = { program_statement };

program_statement = statement |
                    function_declaration;

statement = for_statement |
            if_statement |
            print_statement |
            variable_statement |
            assignment_statement |
            function_call_statement;

for_statement = 'for', '(', identifier, 'in', expression ')', '{', { statement }, '}';

if_statement = 'if', '(', expression, ')', '{', { statement }, '}' [ 'else', '{', { statement }, '}' ];

print_statement = 'print', '(', expression, ')'

variable_statement = 'var', identifier, [':', type_expression, ] '=', expression;

function_call_statement = function_call;

assignment_statement = identifier, '=', expression;

function_declaration = 'function', identifier, '(', parameter_list, ')' + 
                       [ ':' + type_expression ] + "{" + { function_body_statement } + "}";

function_body_statement = statement |
                          return_statement;

parameter_list = [ parameter, {',' parameter } ];

parameter = identifier, [ ':', type_expression ];

return_statement = 'return', expression;

expression = equality_expression;

equality_expression = comparison_expression { ("!=" | "==") comparison_expression };

comparison_expression = term_expression { (">" | ">=" | "<" | "<=" ) additive_expression };

additive_expression = factor_expression { ("+" | "-" ) factor_expression };

factor_expression = unary_expression { ("/" | "*" ) unary_expression };

unary_expression = ( "not" | "-" ) unary_expression | primary_expression;

primary_expression = IDENTIFIER | STRING | INTEGER | "true" | "false" | "null"| 
                     list_literal | function_call | "(", expression, ")"

list_literal = '[', expr, [ { ',', expr } ] ']'; 

function_call = IDENTIFIER, '(', argument_list , ')'

argument_list = [ expr , { ',' , expr } ]

type_expression = 'int' | 'string' | 'bool' | 'object' | 'list', '<' , type_expression, '>'

```

### CatScript Types

CatScript is statically typed, with a small type system as follows

* int - a 32 bit integer
* string - a java-style string
* bool - a boolean value
* list<x> - a list of value with the type 'x'
* null - the null type 
* object - any type of value