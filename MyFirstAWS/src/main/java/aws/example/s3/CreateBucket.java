package aws.example.s3;

import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import java.util.List;

import javax.sound.midi.SysexMessage;

/**
 * Create an Amazon S3 bucket.
 *
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
 */

public class CreateBucket {
	
	public static Bucket getBucket(String bucket_name) {
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		Bucket named_bucket = null;
		List<Bucket> buckets = s3.listBuckets();
		for (Bucket b : buckets) {
			if (b.getName().equals(bucket_name)) {
				named_bucket = b;
			}
		}
		return named_bucket;
	}
	
	public static Bucket createBucket(String bucket_name) {
		
        final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		Bucket named_bucket = null;
		
		if (s3.doesBucketExistV2(bucket_name)) {
			System.out.format(" Bucket %s already exists.\n ", bucket_name);
			named_bucket = getBucket(bucket_name);
		} else {
			try {
				named_bucket = s3.createBucket(bucket_name);
			} catch (AmazonS3Exception ex) {
				System.err.println(ex.getErrorMessage());
			}
		}

		return named_bucket;

	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		final String USAGE = "\n" + "CreateBucket - create an S3 bucket \n\n" + "Usage: CreateBucket <bucketname>\n\n"
				+ "Where: \n bucketname - the name of the bucket to create.\n\n"
				+ "The name of the bucket should be unique, or an error will result.\n";

		if (args.length > 1) {
			System.out.println(USAGE);
			System.exit(1);
		}

		String bucket_name = args[0];
		System.out.format("\nCreating S3 bucket: %s\n", bucket_name);

		Bucket bucket = createBucket(bucket_name);
		if (bucket == null) {
			System.out.println("Error creating bucket!\n");

		} else {
			System.out.println("Bucket " + args[0] + " created successfully\nThank you");

		}

	}

}
