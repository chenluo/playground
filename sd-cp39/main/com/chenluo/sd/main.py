from util.sd_pipe import *

if __name__ == '__main__':
    base, refiner = get_text2img_pipe_with_refiner('cuda')
    prompt = "a hacker is attacking a highly secured system using edging technology. background is the terminal."
    image = base(
        prompt=prompt, num_inference_steps=40,
        denoising_end=0.8,
        output_type="latent", ).images
    image = refiner(
        prompt=prompt,
        num_inference_steps=40,
        denoising_start=0.8,
        image=image).images[0]
    image
    image.save('output.png')
