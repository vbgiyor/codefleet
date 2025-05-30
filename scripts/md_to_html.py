import markdown
import os
import glob
import shutil

# Directory configuration
src_dir = "./docs"
dest_dir = "static/markdown/md_to_html"
frontend_copy_dir = "frontend/public/static/md_to_html"

def convert_md_to_html(input_md_file, output_html_file):
    with open(input_md_file, 'r', encoding='utf-8') as f:
        md_content = f.read()

    # Convert Markdown to HTML with extensions
    md = markdown.Markdown(extensions=[
        'extra', 'codehilite', 'toc', 'pymdownx.superfences'
    ])
    html_content = md.convert(md_content)

    if not html_content.strip():
        print(f"⚠️  Warning: No HTML content generated for: {input_md_file}")

    # HTML template with embedded styles
    html_template = f"""<!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>{os.path.basename(input_md_file)}</title>
            <link rel="stylesheet" href="/static/styles.css">
            <style>
                .markdown-body code, .markdown-body pre {{
                    background-color: #F5F8FA;
                }}
                .markdown-body pre code {{
                    padding-left: 1rem;
                    display: block;
                }}
                .markdown-body {{
                    padding: 2rem;
                    line-height: 2;
                }}
                .markdown-body p {{
                    margin-bottom: 1.5rem;
                }}
                .markdown-body ul, .markdown-body ol {{
                    margin-bottom: 1.5rem;
                    padding-left: 3rem;
                }}
                .markdown-body li {{
                    margin-bottom: 0.75rem;
                }}
                .markdown-body h1, .markdown-body h2, .markdown-body h3 {{
                    margin-bottom: 1.5rem;
                }}
                .markdown-body pre {{
                    margin-bottom: 1.5rem;
                }}
            </style>
        </head>
        <body>
            <div class="navbar">
                <div class="container">
                    <a href="#">Home</a>
                    <a href="#">About</a>
                    <a href="#">Contact</a>
                </div>
            </div>
            <div class="container">
                <div class="markdown-body">
                    {html_content}
                </div>
            </div>
            <div class="footer">
                <div class="container">
                    <a href="#">Privacy Policy</a>
                    <a href="#">Terms of Service</a>
                </div>
            </div>
        </body>
        </html>
        """

    os.makedirs(os.path.dirname(output_html_file), exist_ok=True)

    with open(output_html_file, 'w', encoding='utf-8') as f:
        f.write(html_template)

def convert_all_md_files():
    print(f"🔍 Looking for Markdown files in {src_dir}")
    md_files = glob.glob(f"{src_dir}/**/*.md", recursive=True)

    if not md_files:
        print("⚠️  No Markdown files found!")
        return

    for md_file in md_files:
        relative_path = os.path.relpath(md_file, src_dir)
        html_file_name = os.path.splitext(relative_path)[0] + ".html"
        output_html_file = os.path.join(dest_dir, html_file_name)

        print(f"🛠️  Converting {md_file} → {output_html_file}")
        convert_md_to_html(md_file, output_html_file)

def copy_html_to_frontend():
    print(f"\n📁 Copying HTML files to frontend directory: {frontend_copy_dir}")
    print(f"📁 Absolute target path: {os.path.abspath(frontend_copy_dir)}")

    for root, dirs, files in os.walk(dest_dir):
        for file in files:
            if file.endswith(".html"):
                src_file_path = os.path.join(root, file)
                rel_path = os.path.relpath(src_file_path, dest_dir)
                dest_file_path = os.path.join(frontend_copy_dir, rel_path)

                try:
                    os.makedirs(os.path.dirname(dest_file_path), exist_ok=True)
                    shutil.copy2(src_file_path, dest_file_path)

                    print(f"✅ Copied: {src_file_path} → {dest_file_path}")
                    print(f"   → Verified: {os.path.exists(dest_file_path)}")
                except Exception as e:
                    print(f"❌ Failed to copy: {src_file_path} → {dest_file_path}")
                    print(f"   Error: {e}")

if __name__ == "__main__":
    convert_all_md_files()

    if os.path.exists(frontend_copy_dir):
        copy_html_to_frontend()
    else:
        print(f"⚠️  Skipping copy: {frontend_copy_dir} does not exist (volume not mounted yet?)")

    print("\n✅ Markdown conversion complete.")
