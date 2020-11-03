package edu.montana.csci.csci466;

import edu.montana.csci.csci466.js.JSTranspiler;
import edu.montana.csci.csci466.parser.CatScriptParser;
import edu.montana.csci.csci466.parser.statements.CatScriptProgram;
import edu.montana.csci.csci466.tokenizer.CatScriptTokenizer;
import edu.montana.csci.csci466.tokenizer.Token;
import edu.montana.csci.csci466.util.HTMLParseTreeRenderer;
import edu.montana.csci.csci466.util.Web;
import spark.Spark;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.*;

class CatScriptServer {
    public static void main(String[] args) {
        port(6789);
        Spark.staticFiles.location("/public");
        Web.init();

        get("/", (req, resp) -> {

            String name = req.queryParams("file");
            String code;
            if (name != null && !name.isBlank()) {
                URL resource = CatScriptServer.class.getResource("/scripts/" + name );
                Path path = Paths.get(resource.toURI());
                code = Files.lines(path).collect(Collectors.joining("\n"));
            } else {
                code = "\n" +
                        "  // welcome to cs446!!!\n" +
                        "  var x = \"hello catscript!\"\n" +
                        "  print(x)\n";
            }

            return Web.renderTemplate("templates/index.vm", "code", code);
        });

        get("/tokenize", (req, resp) -> {
            String source = req.queryParams("src");
            List<Token> tokens = new CatScriptTokenizer(source).getTokens();
            return "<pre>" +
                    tokens.stream().map(token -> token.toString() + "\n").collect(Collectors.toList()) +
                    "</pr>";
        });

        get("/parse", (req, resp) -> {
            String source = req.queryParams("src");
            CatScriptProgram program = new CatScriptParser().parse(source);
            return HTMLParseTreeRenderer.render(program);
        });

        get("/evaluate", (req, resp) -> {
            String source = req.queryParams("src");
            CatScriptProgram program = new CatScriptParser().parse(source);
            program.execute();
            return program.getOutput();
        });

        get("/transpile", (req, resp) -> {
            String source = req.queryParams("src");
            CatScriptProgram program = new CatScriptParser().parse(source);
            String jsSource = JSTranspiler.transpile(program);
            String output = JSTranspiler.evalJS(jsSource);
            return "<pre>" + "\n\n  Source =================\n\n" + jsSource + "\n\n  Output =================\n\n" + output + "</pre>";
        });

    }
}