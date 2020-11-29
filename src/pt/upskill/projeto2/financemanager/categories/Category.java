package pt.upskill.projeto2.financemanager.categories;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author upSkill 2020
 * <p>
 * ...
 */

public class Category implements Serializable {
    private String name;
    private List<String> tags = new ArrayList<String>();
    private static final long serialVersionUID = -9107819223195202547L;

    public Category(String name) {
        this.name = name;
    }

    /**
     * Função que lê o ficheiro categories e gera uma lista de {@link Category} (método fábrica)
     * Deve ser utilizada a desserialização de objetos para ler o ficheiro binário categories.
     *
     * @param file - Ficheiro onde estão apontadas as categorias possíveis iniciais, numa lista serializada (por defeito: /account_info/categories)
     * @return uma lista de categorias, geradas ao ler o ficheiro
     */
    public static List<Category> readCategories(File file) {
        List<Category> listaCat = null;
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            listaCat = (List<Category>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return listaCat;
    }

    /**
     * Função que grava no ficheiro categories (por defeito: /account_info/categories) a lista de {@link Category} passada como segundo argumento
     * Deve ser utilizada a serialização dos objetos para gravar o ficheiro binário categories.
     * @param file
     * @param categories
     */
    public static void writeCategories(File file, List<Category> categories) {
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(categories);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /categories");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public String getName() {
        return name;
    }
}
