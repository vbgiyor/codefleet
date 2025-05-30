import markdown
import os

def convert_md_to_html(input_md_file, output_html_file):
    # Read the Markdown file
    with open(input_md_file, 'r', encoding='utf-8') as f:
        md_content = f.read()

    # Convert Markdown to HTML with extensions for tables, code highlighting, etc.
    md = markdown.Markdown(extensions=['extra', 'codehilite', 'toc', 'pymdownx.superfences'])
    html_content = md.convert(md_content)

    # HTML template referencing external styles.css
    html_template = f"""<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>{os.path.basename(input_md_file)}</title>
    <link rel="stylesheet" href="../styles.css">
    <style>
        /* Override code block background to be lighter */
        .markdown-body code, .markdown-body pre {{
            background-color: #F5F8FA;
        }}
        /* Add left margin to code content inside snippets */
        .markdown-body pre code {{
            padding-left: 1rem;
            display: block; 
        }}
        /* Adjust padding and spacing to match Markdown layout */
        .markdown-body {{
            padding: 1rem; /* Increased padding for more space */
            line-height: 1.5; /* Increased line spacing */
        }}
        .markdown-body p {{
            margin-bottom: 1.5rem; /* More space between paragraphs */
        }}
        .markdown-body ul, .markdown-body ol {{
            margin-bottom: 1.5rem; /* More space after lists */
            padding-left: 3rem; /* More indent for lists */
        }}
        .markdown-body li {{
            margin-bottom: 0.75rem; /* More space between list items */
        }}
        .markdown-body h1, .markdown-body h2, .markdown-body h3 {{
            margin-bottom: 1.5rem; /* More space after headings */
        }}
        .markdown-body pre {{
            margin-bottom: 1rem; /* More space after code blocks */
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

    # Write the HTML to the output file
    with open(output_html_file, 'w', encoding='utf-8') as f:
        f.write(html_template)

# Example usage
if __name__ == "__main__":
    input_file = "../docs/python/journal/data_types_type_conversion.md"
    output_file = "../website/frontend/src/md_to_html/data_types_type_conversion.html"
    convert_md_to_html(input_file, output_file)
    print(f"Converted {input_file} to {output_file}")