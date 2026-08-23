import os
from PIL import Image, ImageDraw

def generate_mipmaps():
    path = r'd:\Work\Kotlin\FixMatch\fixmatch-app\app\src\main\res\drawable\app_logo_white.png'
    img = Image.open(path).convert('RGBA')
    
    # We want to crop it nicely so the logo is centered
    bbox = img.convert("L").point(lambda x: 0 if x == 255 else 255).getbbox()
    if bbox:
        # Add margin
        margin = 20
        bbox = (max(0, bbox[0]-margin), max(0, bbox[1]-margin), min(img.width, bbox[2]+margin), min(img.height, bbox[3]+margin))
        img = img.crop(bbox)
        
    w, h = img.size
    size = max(w, h)
    
    square_img = Image.new('RGBA', (size, size), (255, 255, 255, 255))
    square_img.paste(img, ((size - w) // 2, (size - h) // 2))
    
    res_dir = r'd:\Work\Kotlin\FixMatch\fixmatch-app\app\src\main\res'
    sizes = {
        'mdpi': 48,
        'hdpi': 72,
        'xhdpi': 96,
        'xxhdpi': 144,
        'xxxhdpi': 192
    }
    
    for dpi, s in sizes.items():
        # Standard icon
        icon = square_img.resize((s, s), Image.Resampling.LANCZOS)
        dir_path = os.path.join(res_dir, f'mipmap-{dpi}')
        os.makedirs(dir_path, exist_ok=True)
        icon.save(os.path.join(dir_path, 'ic_launcher.png'), 'PNG')
        
        # Round icon
        round_icon = Image.new('RGBA', (s, s), (255, 255, 255, 0))
        mask = Image.new('L', (s, s), 0)
        draw = ImageDraw.Draw(mask)
        draw.ellipse((0, 0, s, s), fill=255)
        
        round_icon.paste(icon, (0, 0), mask=mask)
        round_icon.save(os.path.join(dir_path, 'ic_launcher_round.png'), 'PNG')

if __name__ == '__main__':
    generate_mipmaps()
    print("Mipmaps generated perfectly from white logo!")
