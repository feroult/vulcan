package vulcan.refactorings;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RefactoringInvoker {

    private Gson gson;

    public RefactoringInvoker() {
        this.gson = buildGson();
    }

    public Gson getGson() {
        return gson;
    }

    public Chained getChained(RefactoringArgs args, String body) {
        Type type = new TypeToken<List<AbstractRefactoringBuilder>>() {
        }.getType();

        List<AbstractRefactoringBuilder> builders = gson.fromJson(body, type);

        return new Chained.Builder().setProjectName(args.projectName())
                .setFullyQualifiedName(args.fullyQualifiedName())
                .setRefactorings(builders)
                .build();
    }

    protected Gson buildGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // TODO: use reflection to find all abstract subclasses of AbstractRefactoringBuilder to remove this direct link
        gsonBuilder.registerTypeAdapter(AbstractRefactoringBuilder.class,
                (JsonDeserializer) (json, typeOfT, context) -> {
                    String type = json.getAsJsonObject()
                            .get("type")
                            .getAsString();
                    return context.deserialize(json, getRefactoringBuilderClass(type));
                });
        gsonBuilder.registerTypeAdapter(List.class, new JsonSerializer<List<?>>() {
            @Override
            public JsonElement serialize(List<?> src, Type typeOfSrc, JsonSerializationContext context) {
                if (src.isEmpty()) {
                    return null;
                } else {
                    return context.serialize(src);
                }
            }
        });

        return gsonBuilder.create();
    }


    private Class<? extends AbstractRefactoringBuilder> getRefactoringBuilderClass(String type) {
        try {
            return (Class<? extends AbstractRefactoringBuilder>) Class.forName(getRefactoringBuilderClassName(type));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getRefactoringBuilderClassName(String input) {
        String[] parts = input.split("/");
        String packageName = String.join(".", Arrays.copyOfRange(parts, 0, parts.length - 1));
        String className = parts[parts.length - 1];
        className = className.replaceAll("-", " ");
        className = Arrays.stream(className.split(" "))
                .map(str -> str.substring(0, 1)
                                    .toUpperCase() + str.substring(1))
                .collect(Collectors.joining());
        return "vulcan." + packageName + "." + className + "$Builder";
    }

}
