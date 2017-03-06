package alburraq.cartoon.me;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import alburraq.cartoon.me.gestures.MoveGestureDetector;
import alburraq.cartoon.me.gestures.RotateGestureDetector;
import alburraq.cartoon.me.utils.ImageProcess;
import com.appbrain.AppBrain;
import com.appbrain.AppBrainBanner;


@SuppressLint("NewApi")
public class CropActivity extends Activity implements OnTouchListener {
    
    // Member fields.
	static AlertDialog dialog;
    private ImageView mImg;
    private ImageView mTemplateImg;
    private int mScreenWidth;
    private int mScreenHeight;
    private CropHandler mCropHandler;
    private static ProgressDialog mProgressDialog;
    private int mSelectedVersion;
    static ByteArrayOutputStream bs;
    private Matrix mMatrix = new Matrix();
    private float mScaleFactor = 0.8f;
    private float mRotationDegrees = 0.f;
    private float mFocusX = 0.f;
    private float mFocusY = 0.f;
    private int mImageHeight, mImageWidth;
    private ScaleGestureDetector mScaleDetector;
    private RotateGestureDetector mRotateDetector;
    private MoveGestureDetector mMoveDetector;
    static Bitmap transImg;
    private int mTemplateWidth;
    private int mTemplateHeight;
    TextView change_template;
    private static boolean imageCropped=false;
    // Constants
    public static final int MEDIA_GALLERY = 1;
    public static final int TEMPLATE_SELECTION = 2;
    public static final int DISPLAY_IMAGE = 3;
    int [] start_location,end_location;
    private final static int IMG_MAX_SIZE = 1000;
    private final static int IMG_MAX_SIZE_MDPI = 400;
    Button tour,change_template_tour;
    byte[] byteArray;
    Uri myUriGallery,myUriCamera;
    Bitmap	photoImg;
    //ShowcaseView sv;
    Handler handler;
    Runnable runnable;
    Button crop_button;
    //ShowcaseView.ConfigOptions co;
    static String my_token;
    boolean flag = true;
    ImageButton change_teamplate_tour;
    boolean is_location_load=false;
    @SuppressWarnings({ "deprecation", "hiding", "unused" })
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        AppBrain.init(this);
//        interstitial = new InterstitialAd(CropActivity.this);
//        AdView adView = (AdView) this.findViewById(R.id.adView_crop);
//        interstitial.setAdUnitId(Constants.admob_unit_id);
//   		
//   		// Request for Ads
//   		AdRequest adRequest = new AdRequest.Builder()
//   		
//   		// Add a test device to show Test Ads
//   		 .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//   		 .addTestDevice("")
//   				.build();
//   		
//   		// Load ads into Banner Ads
//   		adView.loadAd(adRequest);
//   		
//   		// Load ads into Interstitial Ads
//   		interstitial.loadAd(adRequest);
        
        AppBrain.init(this);
        AppBrainBanner banner = (AppBrainBanner) findViewById(R.id.appbrain_banner_crop_activity);
        banner.requestAd();
        
    	ActionBar bar = getActionBar();
   	 	bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffba00")));
   	 	int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
   	 	Typeface t = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
	 	TextView abTitle = (TextView) findViewById(titleId);
	 	abTitle.setTextColor(Color.BLACK);
	 	abTitle.setTypeface(t);
   	 	crop_button = (Button) findViewById(R.id.imageButton_crop_image);
   	 	my_token = getIntent().getStringExtra("token");
   	 	change_template_tour = (Button) findViewById(R.id.imageButton_change_template);
   	 	Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
   	 	crop_button.setTypeface(tf);
   	 	change_template_tour.setTypeface(tf);
       // co = new ShowcaseView.ConfigOptions();
        start_location = new int [4];
//        
//        if(my_token==null)
//        {
//        	sv = ShowcaseView.insertShowcaseView(R.id.imageButton_change_template, CropActivity.this, "Crop Your Face", "Change FaceCroper Template according to\nyour face and Crop your Face\nDrag & enlarge the photo\nusing touch", co);
//        	
//        	co.hideOnClickOutside = true;
//        	crop_button.setEnabled(false);
//        }
        try
        {
          myUriGallery = Uri.parse(getIntent().getExtras().getString("imageUri"));
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        
        }
        try
        {
        	myUriCamera = Uri.parse(getIntent().getExtras().getString("image"));
        	
        }
        catch(Exception e)
        {
        	try
        	{

                File pictureFile = (File)getIntent().getExtras().get("byteArray");
                Bitmap image;
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                image = BitmapFactory.decodeFile(pictureFile.getAbsolutePath(), bitmapOptions);
                bs = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG,75, bs);
                byteArray = bs.toByteArray();

//                byteArray=getIntent().getByteArrayExtra("byteArray");
        	}
        	catch(Exception ex)
        	{
        		
        	}
        	e.printStackTrace();
        }
        
        mImg = (ImageView) findViewById(R.id.cp_img);
        mTemplateImg = (ImageView) findViewById(R.id.cp_face_template);
        if(my_token!=null)
        	mImg.setOnTouchListener(this);
        
        // Get screen size in pixels.
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenHeight = metrics.heightPixels;
        mScreenWidth = metrics.widthPixels;
        
        Bitmap faceTemplate = BitmapFactory.decodeResource(getResources(), R.drawable.face_oval);
        mTemplateWidth = faceTemplate.getWidth();
        mTemplateHeight = faceTemplate.getHeight();
      
        if (mScreenWidth == 320 && mScreenHeight == 480) {
            mTemplateWidth = 218;
            mTemplateHeight = 300;
            faceTemplate = Bitmap.createScaledBitmap(faceTemplate, mTemplateWidth, mTemplateHeight, true);
            mTemplateImg.setImageBitmap(faceTemplate);
        }
        
      if(myUriCamera!=null)
      {
    	  Bitmap photoImg = null;
    	  try 
    	  {
    		//  photoImg = Media.getBitmap(this.getContentResolver(), myUriCamera);
    		  BitmapFactory.Options options = new BitmapFactory.Options();
    		  options.inSampleSize = 4;

    		  AssetFileDescriptor fileDescriptor =null;
    		  fileDescriptor=this.getContentResolver().openAssetFileDescriptor(myUriCamera, "r");

    		  Bitmap actuallyUsableBitmap= BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
    		  photoImg=actuallyUsableBitmap;
    		  Matrix m = new Matrix();
    		  m.postRotate(90);
    		  photoImg = Bitmap.createBitmap(photoImg,0,0,photoImg.getWidth(),photoImg.getHeight(),m,true);
    	  } catch (FileNotFoundException e)
    	  {
    		  // TODO Auto-generated catch block
    		  e.printStackTrace();
    	  } catch (IOException e) 
    	  {
    		  // TODO Auto-generated catch block
    		  e.printStackTrace();
    	  }
    	Display display = getWindowManager().getDefaultDisplay();
		double width = display.getWidth();  // deprecated
		double height = display.getHeight();  // deprecated
	    int startwidth = 0, startHeight = 0;
	    if(photoImg!=null)
	    {
	    	if(height>photoImg.getHeight() || width> photoImg.getWidth())
	    	{
	    		mImg.setImageBitmap(photoImg);
	    	}
	    	else
	    		mImg.setImageBitmap(photoImg);
	    
	    }
	    else
	    {
	  	  AlertDialog.Builder alert = new Builder(CropActivity.this);
		  alert.setTitle(R.string.alert);
		  alert.setMessage(R.string.image_corrupted);
		  alert.setPositiveButton("Ok", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
				Intent intent = new Intent (CropActivity.this,MainActivity.class);
				startActivity(intent);
				
			}
		});
		  AlertDialog ad = alert.create();
		  ad.show();
	    }
      }
      if(myUriGallery!=null)
      {
    	  Bitmap photoImg = null;
    	  try
		  {		
    		  	  
    		  photoImg = Media.getBitmap(this.getContentResolver(), myUriGallery);
    		  if(photoImg!=null)
    		  {
				  int width = photoImg.getWidth();
				  int height = photoImg.getHeight();
				  if(height>1280 && width>960)
				  {
					  BitmapFactory.Options options = new BitmapFactory.Options();
					  	options.inSampleSize = 4;
					  	AssetFileDescriptor fileDescriptor =null;
					  	photoImg.recycle();
					  	photoImg= null;
					  	fileDescriptor=this.getContentResolver().openAssetFileDescriptor(myUriGallery, "r");
					  	Bitmap actuallyUsableBitmap= BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
					  	photoImg=actuallyUsableBitmap;
					
				  }
				    int nh = (int) ( photoImg.getHeight() * (512.0 / photoImg.getWidth()) );
			    	Bitmap scaled = Bitmap.createScaledBitmap(photoImg, 512, nh, true);
			        mImg.setImageBitmap(scaled);
    		  }
			  else
			  {
				
				  AlertDialog.Builder alert = new Builder(CropActivity.this);
				  alert.setTitle("Alert!");
				  alert.setMessage(R.string.alert);
				  alert.setPositiveButton("Ok", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
						Intent intent = new Intent (CropActivity.this,MainActivity.class);
						startActivity(intent);
						
					}
				});
				  AlertDialog ad = alert.create();
				  ad.show();
			  }
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
			  AlertDialog.Builder alert = new Builder(CropActivity.this);
			  alert.setTitle("Alert!");
			  alert.setMessage(R.string.alert);
			  alert.setPositiveButton("Ok", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
					Intent intent = new Intent (CropActivity.this,MainActivity.class);
					startActivity(intent);
					
				}
			});
			  AlertDialog ad = alert.create();
			  ad.show();
		  }    		
    	
      }
      if(byteArray!=null)
      {
    	  Bitmap photoImg = null;
    	  try
    	  {
    		  photoImg = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    		  mImg.setImageBitmap(photoImg);
    	  }
    	  catch(Exception e)
    	  {
    		  AlertDialog.Builder alert = new Builder(CropActivity.this);
			  alert.setTitle("Alert!");
			  alert.setMessage(R.string.alert);
			  alert.setPositiveButton("Ok", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
					Intent intent = new Intent (CropActivity.this,MainActivity.class);
					startActivity(intent);
					
				}
			});
			  AlertDialog ad = alert.create();
			  ad.show();
    	  }
      }
 
        // View is scaled by matrix, so scale initially
        mMatrix.postScale(mScaleFactor, mScaleFactor);
       // mImg.setImageMatrix(mMatrix);
        mImg.setImageMatrix(mMatrix);
        // Setup Gesture Detectors
        mScaleDetector = new ScaleGestureDetector(getApplicationContext(), new ScaleListener());
        mRotateDetector = new RotateGestureDetector(getApplicationContext(), new RotateListener());
        mMoveDetector = new MoveGestureDetector(getApplicationContext(), new MoveListener());
        
        // Instantiate Thread Handler.
        mCropHandler = new CropHandler(this,CropActivity.this);
        mImg.setOnTouchListener(this);
		
    }

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
	      super.onWindowFocusChanged(hasFocus);
		  change_template_tour.getLocationInWindow(start_location);
		  Display display = getWindowManager().getDefaultDisplay();
		  final Point size = new Point();
		  display.getSize(size);
		  float height = size.y;
		  @SuppressWarnings("unused")
		float centerY=height/2;
		  float width = size.x;
		  @SuppressWarnings("unused")
		float centerX = width/2;
//		  if(sv!=null)
//		  {
//			  sv.animateGesture(centerX-start_location[0], centerY-start_location[1],0,0);
//		  }
//		 
	}
	public void onCropImageButton(View v) {
        // Create progress dialog and display it.
        mProgressDialog = new ProgressDialog(v.getContext());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Cropping Image\nPlease Wait.....");
        mProgressDialog.show();

        // Setting values so that we can retrive the image from 
        // ImageView multiple times.
        mImg.buildDrawingCache(true);
        mImg.setDrawingCacheEnabled(true);
        mTemplateImg.buildDrawingCache(true);
        mTemplateImg.setDrawingCacheEnabled(true);
        
        // Create new thread to crop.
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Crop image using the correct template size.
                Bitmap croppedImg = null;
                if (mScreenWidth == 320 && mScreenHeight == 480) {
                    if (mSelectedVersion == MainActivity.VERSION_1) {
                        croppedImg = ImageProcess.cropImage(mImg.getDrawingCache(true), mTemplateImg.getDrawingCache(true), mTemplateWidth, mTemplateHeight);
                    } else {
                        croppedImg = ImageProcess.cropImageVer2(mImg.getDrawingCache(true), mTemplateImg.getDrawingCache(true), mTemplateWidth, mTemplateHeight);
                    }
                } else {
                    if (mSelectedVersion == MainActivity.VERSION_1) {
                        croppedImg = ImageProcess.cropImage(mImg.getDrawingCache(true), mTemplateImg.getDrawingCache(true), mTemplateWidth, mTemplateHeight);
                    } else {
                        croppedImg = ImageProcess.cropImageVer2(mImg.getDrawingCache(true), mTemplateImg.getDrawingCache(true), mTemplateWidth, mTemplateHeight);
                    }
                }
                mImg.setDrawingCacheEnabled(false);
                mTemplateImg.setDrawingCacheEnabled(false);
                
                // Send a message to the Handler indicating the Thread has finished.
                mCropHandler.obtainMessage(DISPLAY_IMAGE, -1, -1, croppedImg).sendToTarget();
                imageCropped=true;
            }
        }).start();
    }
    
    public void onChangeTemplateButton(View v) {
//    	if(sv!=null)
//    		sv.setVisibility(View.GONE);
    	
    	//crop_button.setEnabled(true);
        Intent intent = new Intent(this, TemplateSelectDialogActivity.class);
        startActivityForResult(intent, TEMPLATE_SELECTION);
    }
    
    
    /*
     * Adjust the size of bitmap before loading it to memory.
     * This will help the phone by not taking up a lot memory.
     */
    private void setSelectedImage(String path) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        if (mScreenWidth == 320 && mScreenHeight == 480) {
            options.inSampleSize = calculateImageSize(options, IMG_MAX_SIZE_MDPI);
        } else {
            options.inSampleSize = calculateImageSize(options, IMG_MAX_SIZE);
        }
        
        options.inJustDecodeBounds = false;
        Bitmap photoImg = BitmapFactory.decodeFile(path, options);
        mImageHeight = photoImg.getHeight();
        mImageWidth = photoImg.getWidth();
        mImg.setImageBitmap(photoImg);
    }

    /*
     * Retrieves the path to the selected image from the Gallery app.
     */
    private String getGalleryImagePath(Intent data) {
        Uri imgUri = data.getData();
        String filePath = "";
        if (data.getType() == null) {
            // For getting images from gallery.
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(imgUri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        } 
        return filePath;
    }
    
    /*
     * Calculation used to determine by what factor images need to be reduced by.
     * Images with its longest side below the threshold will not be resized.
     */
    private int calculateImageSize(BitmapFactory.Options opts, int threshold) {
        int scaleFactor = 1;
        final int height = opts.outHeight;
        final int width = opts.outWidth;

        if (width >= height) {
            scaleFactor = Math.round((float) width / threshold);
        } else {
            scaleFactor = Math.round((float) height / threshold);
        }
        return scaleFactor;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == MEDIA_GALLERY) {
                String path = getGalleryImagePath(data);
                setSelectedImage(path);
            } else if (requestCode == TEMPLATE_SELECTION) {
                int pos = data.getExtras().getInt(TemplateSelectDialogActivity.POSITION);
                Bitmap templateImg = null;
                
                // Change template according to what the user has selected.
                switch(pos) {
                case 0:
                    templateImg = BitmapFactory.decodeResource(getResources(), R.drawable.face_oblong);
                    break;
                case 1:
                    templateImg = BitmapFactory.decodeResource(getResources(), R.drawable.face_oval);
                    break;
                case 2:
                    templateImg = BitmapFactory.decodeResource(getResources(), R.drawable.face_round);
                    break;
                case 3:
                    templateImg = BitmapFactory.decodeResource(getResources(), R.drawable.face_square);
                    break;
                case 4:
                    templateImg = BitmapFactory.decodeResource(getResources(), R.drawable.face_triangular);
                    break;
                }
                
                mTemplateWidth = templateImg.getWidth();
                mTemplateHeight = templateImg.getHeight();
                
                // Resize template if necessary.
                if (mScreenWidth == 320 && mScreenHeight == 480) {
                    mTemplateWidth = 218;
                    mTemplateHeight = 300;
                    templateImg = Bitmap.createScaledBitmap(templateImg, mTemplateWidth, mTemplateHeight, true);
                }
                mTemplateImg.setImageBitmap(templateImg);
            }
        }
    }
    
    private static class CropHandler extends Handler {
        Context context;
        Activity activity;
        CropHandler(Context context, Activity activity) {

            this.context = context;
            this.activity=activity;
        }

        @SuppressLint("InflateParams")
		@Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            
            if (msg.what == DISPLAY_IMAGE) {
                mProgressDialog.dismiss();
                final Bitmap cropImg = (Bitmap) msg.obj;
                
                // Setup an AlertDialog to display cropped image.
                LayoutInflater li = LayoutInflater.from(context);
     		    View view = li.inflate(R.layout.cropped_image_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(view);
                ImageView icon = (ImageView) view.findViewById(R.id.imageView_custom_dialog);
                icon.setImageBitmap(cropImg);
                TextView text = (TextView) view.findViewById(R.id.textView_cropped_image_dilaog);
                Button continue_crop_image = (Button) view.findViewById(R.id.button_cropped_image_dialog);
                Button cancel_crop_image = (Button) view.findViewById(R.id.button_cropped_image_dialog_cancel);
                Typeface tf = Typeface.createFromAsset(activity.getAssets(), "Roboto-Light.ttf");
                continue_crop_image.setTypeface(tf);
                cancel_crop_image.setTypeface(tf);
                text.setTypeface(tf);
                continue_crop_image.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						 
						 transImg=cropImg;
                         if(imageCropped)
                         {
                     		bs = new ByteArrayOutputStream();
                     		transImg.compress(Bitmap.CompressFormat.PNG, 50, bs);
                     		byte [] byteArray = bs.toByteArray();
                     		Intent intent = new Intent (context,FingerPaintActivity.class);
                     		intent.putExtra("final_image", byteArray);
                     		intent.putExtra("token", my_token);
                     		context.startActivity(intent);
                     		activity.finish();
                     		}
                         else
                         {
                     		Toast.makeText(context,R.string.crop_image_first,Toast.LENGTH_LONG).show();
                         }
						
					}
				});
                cancel_crop_image.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
                dialog = builder.create();
                dialog.show();
            }
        }
    }
    
    
    @SuppressLint("ClickableViewAccessibility")
	public boolean onTouch(View v, MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        mRotateDetector.onTouchEvent(event);
        mMoveDetector.onTouchEvent(event);

        float scaledImageCenterX = (mImageWidth * mScaleFactor) / 2;
        float scaledImageCenterY = (mImageHeight * mScaleFactor) / 2;

        mMatrix.reset();
        mMatrix.postScale(mScaleFactor, mScaleFactor);
        mMatrix.postRotate(mRotationDegrees, scaledImageCenterX, scaledImageCenterY);
        mMatrix.postTranslate(mFocusX - scaledImageCenterX, mFocusY - scaledImageCenterY);

        ImageView view = (ImageView) v;
        view.setImageMatrix(mMatrix);
        return true;
    }
    
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));

            return true;
        }
    }

    private class RotateListener extends RotateGestureDetector.SimpleOnRotateGestureListener {
        @Override
        public boolean onRotate(RotateGestureDetector detector) {
            mRotationDegrees -= detector.getRotationDegreesDelta();
            return true;
        }
    }

    private class MoveListener extends MoveGestureDetector.SimpleOnMoveGestureListener {
        @Override
        public boolean onMove(MoveGestureDetector detector) {
            PointF d = detector.getFocusDelta();
            mFocusX += d.x;
            mFocusY += d.y;

            return true;
        }
    }


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
    
    
}
