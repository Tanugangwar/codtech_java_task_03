import org.apache.mahout.cf.taste.eval.DataModelBuilder;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Load data from CSV (userID, itemID, preference)
            DataModel model = new FileDataModel(new File("data.csv"));

            // Use Pearson similarity
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Find 3 nearest neighbors
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);

            // Build recommender
            UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Recommend 3 items for user 1
            List<RecommendedItem> recommendations = recommender.recommend(1, 3);
            for (RecommendedItem recommendation : recommendations) {
                System.out.println("Recommended Item: " + recommendation.getItemID() +
                        ", Value: " + recommendation.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
