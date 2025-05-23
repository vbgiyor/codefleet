🌐 Deploy a Static Website on AWS (Free Tier) with GitHub & Custom Domain
=========================================================================

A beginner-friendly, cost-conscious guide to host your static website on AWS, connect it with GitHub for automatic deployment, and set up a custom domain.

* * * * *

✅ Step 1: Create an AWS Account
-------------------------------

1.  Visit [aws.amazon.com](https://aws.amazon.com) and click **"Create an AWS Account."**

2.  Enter your email, password, and AWS account name.

3.  Add billing information (a $1 USD hold may be applied and refunded).\
    📎 [Free Tier FAQs](https://aws.amazon.com/free/registration-faqs/)

4.  Verify your identity via phone or email.

5.  Choose the **"Basic Support -- Free"** plan.

💸 **Cost**: Free to create. [AWS Free Tier](https://aws.amazon.com/free/) is available for 12 months for new users.

* * * * *

🌍 Step 2: Register a Domain Using Amazon Route 53
--------------------------------------------------

1.  Log in to the [AWS Management Console](https://console.aws.amazon.com).

2.  Navigate to **Route 53**.

3.  Select **"Register Domain."**

4.  Search for your domain (e.g., `mywebsite.com`) and select an available one.

5.  Add to cart and proceed to checkout.

6.  Provide registrant details.\
    📎 [Domain Registration Docs](https://docs.aws.amazon.com/Route53/latest/DeveloperGuide/domain-register.html)

7.  Confirm registration via email.

8.  A hosted zone is automatically created.

💸 **Cost**:

-   Domain: ~$12/year for `.com`

-   Hosted Zone: $0.50/month\
    📎 [Route 53 Pricing](https://aws.amazon.com/route53/pricing/)

* * * * *

🗂️ Step 3: Set Up Amazon S3 for Static Hosting
-----------------------------------------------

1.  Open **S3** from the AWS Console.

2.  Click **"Create Bucket."**

3.  Name the bucket (e.g., `mywebsite.com`) and select a region.

4.  **Uncheck** "Block all public access."

5.  Enable **"Static website hosting"** in properties.

6.  Set your index (`index.html`) and error page (`error.html`).

7.  Note the **S3 website endpoint** (e.g., `http://mywebsite.com.s3-website-us-east-1.amazonaws.com`).

💸 **Cost**:

-   Free Tier: 5 GB storage, 20,000 GET, 2,000 PUT requests/month\
    📎 [S3 Hosting Cost](https://aws.amazon.com/getting-started/hands-on/host-static-website/services-costs/)

* * * * *

🚀 Step 4: Configure CloudFront CDN
-----------------------------------

1.  Open **CloudFront** and click **"Create Distribution."**

2.  Choose "Web" and use your S3 website endpoint as origin.

3.  Under **CNAMEs**, enter your custom domain (e.g., `mywebsite.com`).

4.  In **ACM (AWS Certificate Manager)**:

    -   Request a public certificate for your domain.

    -   Choose DNS validation (auto with Route 53).\
        📎 [ACM Docs](https://docs.aws.amazon.com/acm/latest/userguide/gs-acm-request-public.html)

5.  Select this certificate in CloudFront.

6.  Set default root object as `index.html`.

7.  Create distribution (wait 5--10 minutes).

8.  Note your **CloudFront URL** (e.g., `d12345678.cloudfront.net`).

💸 **Cost**:

-   Free Tier: 50 GB data out, 2M requests/month\
    📎 [CloudFront Pricing](https://aws.amazon.com/getting-started/hands-on/host-static-website/services-costs/)

* * * * *

🔄 Step 5: Connect GitHub Repo via AWS Amplify
----------------------------------------------

1.  In AWS Console, open **Amplify**.

2.  Click **"New App" > "Host web app."**

3.  Choose **GitHub**, authenticate, and select your repo and branch.

4.  Amplify auto-detects settings (React, Vue, HTML, etc.).

5.  Save & deploy --- Amplify builds and deploys your site to a temp URL.

💸 **Cost**:

-   Free Tier: 1,000 build minutes/month\
    📎 [Amplify Pricing](https://aws.amazon.com/free/webapps/)

* * * * *

🌐 Step 6: Link Your Custom Domain to Amplify
---------------------------------------------

1.  In Amplify, go to **"Domain management."**

2.  Add your domain (e.g., `mywebsite.com`).

3.  Amplify shows DNS records to add in Route 53.

4.  In Route 53 hosted zone:

    -   Add a **CNAME** record for `www.mywebsite.com` → Amplify domain.

    -   Add an **ALIAS** record for apex domain → Amplify root URL.

5.  Wait for DNS propagation (can take up to 48 hours).

💸 **Cost**:

-   DNS Queries: ~$0.40/million (minimal for low traffic)\
    📎 [Route 53 Pricing](https://aws.amazon.com/route53/pricing/)

* * * * *

📂 Step 7: Upload and Deploy Code
---------------------------------

1.  Push updates to your GitHub repository.

2.  Amplify automatically rebuilds and redeploys.

3.  Visit your domain (e.g., `https://mywebsite.com`) to verify.

💸 **Cost**: Included in Free Tier\
📎 [Hosting with Amplify](https://aws.amazon.com/getting-started/hands-on/host-static-website/)

* * * * *

🧹 Step 8: Clean Up Resources to Avoid Charges
----------------------------------------------

1.  Delete resources if no longer needed:

    -   **Amplify**: App settings → General → Delete app.

    -   **S3**: Delete bucket.

    -   **CloudFront**: Disable & delete distribution.

    -   **Route 53**: Delete hosted zone (no charge if within 12 hours).

2.  Domain registration fees are non-refundable.\
    📎 [Clean-Up Guide](https://docs.aws.amazon.com/Route53/latest/DeveloperGuide/domain-register.html)

* * * * *

💰 Summary of Costs
-------------------

| Item | Cost (Estimate) | Free Tier Covered? |
| --- | --- | --- |
| AWS Account | Free | ✅ |
| S3 Hosting | ~$0--3/month | ✅ (5 GB, 20K GET) |
| CloudFront CDN | ~$0--3/month | ✅ (50 GB, 2M requests) |
| Amplify | ~$0--3/month | ✅ (1,000 build mins) |
| Domain | ~$9--$100/year | ❌ |
| Route 53 Hosted Zone | $0.50/month | ❌ |
| DNS Queries | ~$0.40/million | ❌ (but negligible) |

* * * * *

📌 Notes
--------

-   Monitor usage with [AWS Budgets](https://aws.amazon.com/aws-cost-management/aws-budgets/).

-   Ensure your GitHub repo has static content (HTML/CSS/JS).

-   Costs are minimal if you stay within Free Tier.

-   For dynamic websites (e.g., WordPress), consider [Lightsail](https://aws.amazon.com/lightsail/) instead.